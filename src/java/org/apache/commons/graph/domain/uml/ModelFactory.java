package org.apache.commons.graph.domain.uml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.Enumeration;

import java.util.zip.*;

import org.xml.sax.InputSource;

import org.apache.commons.graph.domain.uml.exception.*;

import org.apache.log4j.Category;

import ru.novosoft.uml.xmi.XMIReader;
import ru.novosoft.uml.model_management.MModel;

/**
 * Description of the Class
 */
public class ModelFactory
{
    private XMIReader xmiReader = null;
    private final static String xmiVersion = "1.1";
    private static Category log =
        Category.getInstance(org.apache.commons.graph.domain.uml.ModelFactory.class);

    /**
     * Constructor for the ModelFactory object
     *
     * @exception Exception
     */
    public ModelFactory()
        throws Exception
    {
        log.debug("ModelFactory.__init__()");
        try
        {
            xmiReader = new XMIReader();
        }
        catch (Exception e)
        {
            log.error(e);
            throw e;
        }
    }

    /**
     * Gets the fromStream attribute of the ModelFactory object
     */
    public MModel getFromStream(InputStream stream)
    {
        log.debug("getFromStream");
        return xmiReader.parse(new InputSource(stream));
    }

    /**
     * Gets the fromXMI attribute of the ModelFactory object
     */
    public MModel getFromXMI(File xmiFile)
        throws IOException
    {
        log.debug("getFromXMI(" + xmiFile.getName() + ")");
        return getFromStream(new FileInputStream(xmiFile));
    }

    /**
     * Gets the fromZargo attribute of the ModelFactory object
     */
    public MModel getFromZargo(File zargoFile)
        throws IOException
    {
        log.debug("getFromZargo(" + zargoFile.getName() + ")");
        MModel RC = null;

        ZipFile zargoZip = new ZipFile(zargoFile);

        Enumeration entries = zargoZip.entries();
        while (entries.hasMoreElements())
        {
            ZipEntry entry = (ZipEntry) entries.nextElement();

            if (entry.getName().endsWith(".xmi"))
            {
                log.debug("Zargo Entry: " + entry.getName());
                return getFromStream(zargoZip.getInputStream(entry));
            }
        }
        throw new FileNotFoundException();
    }

}
