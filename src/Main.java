import java.io.File;
import java.util.concurrent.ForkJoinPool;

public class Main
{
    public static void main(String[] args)
    {
        String folderPath = "/Users/sortedmap/Desktop";
        File file = new File(folderPath);

        long start = System.currentTimeMillis();

        FolderSizeCalculator calculator =
            new FolderSizeCalculator(file);
        ForkJoinPool pool = new ForkJoinPool();
        long size = pool.invoke(calculator);
        System.out.println(size);

        //System.out.println(getFolderSize(file));

        long duration = System.currentTimeMillis() - start;
        System.out.println(duration + " ms");
    }

    public static long getFolderSize(File folder)
    {
        if(folder.isFile()) {
            return folder.length();
        }
        long sum = 0;
        File[] files = folder.listFiles();
        for(File file : files) {
            sum += getFolderSize(file);
        }
        return sum;
    }

    //TODO: 24B, 234Kb, 36Mb, 34Gb, 42Tb
    public String getHumanReadableSize(long size)
    {
        return "";
    }

    //TODO: 24B, 234Kb, 36Mb, 34Gb, 42Tb
    //  24B, 234K, 36M, 34G, 42T
    //  235K => 240640
    public long getSizeFromHumanReadable(String size)
    {

        return 0;
    }
}
