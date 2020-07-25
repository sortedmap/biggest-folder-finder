import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println(getSizeFromHumanReadable("235K"));
        System.exit(0);

        String folderPath = "/Users/sortedmap/Desktop";
        File file = new File(folderPath);

        long start = System.currentTimeMillis();

        FolderSizeCalculator calculator =
            new FolderSizeCalculator(file);
        ForkJoinPool pool = new ForkJoinPool();
        long size = pool.invoke(calculator);
        System.out.println(size);

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
    public static String getHumanReadableSize(long size)
    {
        return "";
    }

    //TODO: 24B, 234Kb, 36Mb, 34Gb, 42Tb
    //  24B, 234K, 36M, 34G, 42T
    //  235K => 240640
    public static long getSizeFromHumanReadable(String size)
    {
        HashMap<Character, Integer> char2multiplier = getMultipliers();
        char sizeFactor = size
            .replaceAll("[0-9\\s+]+", "")
            .charAt(0);
        int multiplier = char2multiplier.get(sizeFactor);
        long length = multiplier * Long.valueOf(
            size.replaceAll("[^0-9]", "")
        );
        return length;
    }

    private static HashMap<Character, Integer> getMultipliers()
    {
        char[] multipliers = {'B', 'K', 'M', 'G', 'T'};
        HashMap<Character, Integer> char2multiplier = new HashMap<>();
        for(int i = 0; i < multipliers.length; i++)
        {
            char2multiplier.put(
                multipliers[i],
                (int) Math.pow(1024, i)
            );
        }
        return char2multiplier;
    }
}
