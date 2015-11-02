package jobs.utils;

import java.io.Serializable;
import java.nio.file.Path;

/**
 * Imports entites from a internal app folder.
 *
 * @param <E> The entity to import.
 * @author Sebastian Sachtleben
 */
public abstract class FolderImporter<E extends Serializable> extends EntityImporter<E> {

    /**
     * Returns the path to the folder which files should be imported.
     *
     * @return The path object
     */
    public abstract Path getFolderPath();

    /**
     * Returns boolean if the folder should be imported recursive. Can be overridden, by default true.
     *
     * @return The recursive boolean
     */
    public boolean loadRecursive() {
        return true;
    }

    @Override
    protected void process() {
        importFromPath(getFolderPath(), loadRecursive());
    }

}
