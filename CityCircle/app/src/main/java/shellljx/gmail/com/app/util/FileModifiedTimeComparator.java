package shellljx.gmail.com.app.util;

import java.io.File;
import java.util.Comparator;

/**
 * Created by Administrator on 2015/6/22.
 */
public class FileModifiedTimeComparator implements Comparator<File> {

    public FileModifiedTimeComparator() {

    }

    @Override
    public int compare(File object1, File object2) {
        long modifiedTime1 = object1.lastModified();
        long modifiedTime2 = object2.lastModified();

        if (modifiedTime1 > modifiedTime2) {
            return -1;
        } else if (modifiedTime1 < modifiedTime2) {
            return 1;
        } else {
            return 0;
        }
    }

}
