package org.apache.commons.graph.domain.uml;

/**
 * StateMachineFactory This class will build a State Machine from an NSUML Model
 * which can be used for testing.
 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.graph.domain.statemachine.State;
import org.apache.commons.graph.domain.statemachine.StateMachine;
import org.apache.commons.graph.domain.statemachine.Transition;
import org.apache.commons.graph.domain.uml.exception.ModelNotFoundException;
import org.apache.commons.graph.exception.GraphException;
import org.apache.log4j.Category;

import ru.novosoft.uml.behavior.state_machines.MCompositeState;
import ru.novosoft.uml.behavior.state_machines.MEvent;
import ru.novosoft.uml.behavior.state_machines.MFinalState;
import ru.novosoft.uml.behavior.state_machines.MGuard;
import ru.novosoft.uml.behavior.state_machines.MPseudostate;
import ru.novosoft.uml.behavior.state_machines.MState;
import ru.novosoft.uml.behavior.state_machines.MStateMachine;
import ru.novosoft.uml.behavior.state_machines.MStateVertex;
import ru.novosoft.uml.behavior.state_machines.MTransition;
import ru.novosoft.uml.foundation.core.MClass;
import ru.novosoft.uml.foundation.data_types.MPseudostateKind;
import ru.novosoft.uml.model_management.MModel;

/**
 * Description of the Class
 */
public class StateMachineFactory
{
    private static Category log =
        Category.getInstance(org.apache.commons.graph.domain.uml.StateMachineFactory.class);

    private MModel extent = null;

    private Map stateNames = new HashMap();// MSTATEVERTEX X NAME

    /**
     * Constructor for the StateMachineFactory object
     *
     * @param extent
     */
    public StateMachineFactory(MModel extent)
    {
        this.extent = extent;
    }

    /**
     * Gets the name attribute of the StateMachineFactory object
     */
    private String getName(String namespace, MStateVertex msv)
    {
        if (msv.getName() != null)
        {
            return namespace + "/" + msv.getName();
        }

        if (msv instanceof MPseudostate)
        {
            return namespace + "/_" +
                ((MPseudostate) msv).getKind().getName() + "_";
        }

        if (msv instanceof MFinalState)
        {
            return namespace + "/_final_";
        }

        return namespace + "/_unknown_";
    }

    /**
     * Description of the Method
     */
    private StateMachine makeStateMachine(String namespace,
                                          MCompositeState mcs)
        throws GraphException
    {
        log.debug("makeStateMachine(" + getName(namespace, mcs) + "): Entry");
        StateMachine RC = new StateMachine(namespace);

        // Step 1 - Add States to the State Machine
        Iterator states =
            mcs.getSubvertices().iterator();

        while (states.hasNext())
        {
            MStateVertex msv =
                (MStateVertex) states.next();

            RC.addState(getName(namespace, msv));

            stateNames.put(msv, getName(namespace, msv));

            if (msv instanceof MPseudostate)
            {
                if (((MPseudostate) msv).getKind() ==
                    MPseudostateKind.INITIAL)
                {
                    RC.setStartState(RC.getState(getName(namespace, msv)));
                }
            }

            if (msv instanceof MCompositeState)
            {
                StateMachine ssm = makeStateMachine(getName(namespace, msv),
                    (MCompositeState) msv);
                RC.getState(getName(namespace, msv)).setSubmachine(ssm);
            }

            if (msv instanceof MFinalState)
            {
                RC.addFinalState(RC.getState(getName(namespace, msv)));
            }
        }

        // Step 2 - Add Transitions to State Machine
        states = mcs.getSubvertices().iterator();
        while (states.hasNext())
        {
            MStateVertex msv = (MStateVertex) states.next();
            String msvName = getName(namespace, msv);

            Iterator transes =
                msv.getIncomings().iterator();
            while (transes.hasNext())
            {
                MTransition trans =
                    (MTransition) transes.next();
                MEvent trigger = trans.getTrigger();
		MGuard guard = trans.getGuard();

                String trigStr = null;
		String guardStr = null;

		if (guard != null) {
		  guardStr = guard.getName();
		}

                if (trigger != null)
                {
                    trigStr = trigger.getName();
                }
                else
                {
                    trigStr = Transition.EPSILON;
                }

                String sourceName =
                    (String) stateNames.get(trans.getSource());
                String targetName =
                    (String) stateNames.get(trans.getTarget());
	       
                State source = RC.getState(sourceName);
                State target = RC.getState(targetName);

		String transName = source + "-" + target + "/" + trigStr;
		if (guardStr != null) {
		  transName = transName + "[" + guardStr + "]";
		}

                Transition tranx =
                    new Transition(transName,
                    source, target);
		tranx.setTrigger( trigStr );
		tranx.setGuard( guardStr );

                RC.addTransition(tranx);
            }
        }

        log.debug("makeStateMachine(" + getName(namespace, mcs) + "): Exit");
        return RC;
    }


    /**
     * Description of the Method
     */
    public StateMachine makeStateMachine(String selector)
        throws GraphException, ModelNotFoundException
    {
        log.debug("makeStateMachine(" + selector + "):Enter");
        MStateMachine model = null;
        StateMachine RC = null;

        Iterator owned =
            extent.getOwnedElements().iterator();

        while (owned.hasNext())
        {
            Object next = owned.next();
            if (next instanceof
                ru.novosoft.uml.foundation.core.MClass)
            {
                MClass mClass = (MClass) next;

                if (selector.equals(mClass.getName()))
                {
                    Iterator machines = mClass.getBehaviors().iterator();
                    if (machines.hasNext())
                    {
                        model = (MStateMachine) machines.next();
                        log.info("StateMachine Found: " + model);
                    }
                }
            }
        }

        if (model == null)
        {
            throw new ModelNotFoundException("Cannot find StateMachine for " +
                selector);
        }

        MState top = model.getTop();
        if (top instanceof MCompositeState)
        {
            RC = makeStateMachine(selector,
                (MCompositeState) top);
        }
        else
        {
            throw new ModelNotFoundException("Expecting CompositeState at top.");
        }

        log.debug("makeStateMachine(" + selector + "):Exit");
        return RC;
    }
}

