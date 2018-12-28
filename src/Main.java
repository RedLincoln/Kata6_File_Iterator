
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        File file =  new File("/");
        File[] files = file.listFiles();
        Iterator<String>  iterator = getAbsolutePathIterator(recursiveIteratorOfFiles(files, 2));
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }

    private static Iterator<File> recursiveIteratorOfFiles(File[] files) {
        return recursiveIteratorOfFiles(files, 1);
    }

    private static Iterator<File> recursiveIteratorOfFiles(File[] files, int maxDepth) {
        if (maxDepth < 1) return null;
        return new Iterator(){
            private int index = 0;
            private Iterator<File> nextIterator = null;

            @Override
            public boolean hasNext() {
                return index < files.length
                        || (nextIterator != null && nextIterator.hasNext());
            }

            @Override
            public Object next() {
                if (nextIterator != null && nextIterator.hasNext()) return nextIterator.next();
                File file =  files[index++];
                if (file.isDirectory())
                    nextIterator =  (file.listFiles() == null )? null : recursiveIteratorOfFiles(file.listFiles(), maxDepth - 1);
                return file;
            }
        };
    }

    private static Iterator<File> iteratorOf(File[] files) {
        return new Iterator(){
            private int index = 0;
            @Override
            public boolean hasNext() {
                return index < files.length;
            }

            @Override
            public Object next() {
                return files[index++];
            }
        };
    }

    private static Iterator<String> getAbsolutePathIterator(Iterator<File> iterator){
        return new Iterator(){
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public Object next() {
                return iterator.next().getAbsolutePath();
            }
        };
    }



}
