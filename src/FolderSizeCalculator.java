import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class FolderSizeCalculator extends RecursiveTask<Long>
{
    private Node node;

    public FolderSizeCalculator(Node node) {
        this.node = node;
    }

    @Override
    protected Long compute()
    {
        File folder = node.getFolder();
        if(folder.isFile())
        {
            long length = folder.length();
            node.setSize(length);
            return length;
        }

        long sum = 0;
        List<FolderSizeCalculator> subTasks = new LinkedList<>();

        File[] files = folder.listFiles();
        for(File file : files)
        {
            Node child = new Node(file);
            FolderSizeCalculator task = new FolderSizeCalculator(child);
            task.fork();
            subTasks.add(task);
            node.addChild(child);
        }

        for(FolderSizeCalculator task : subTasks){
            sum += task.join();
        }

        node.setSize(sum);
        return sum;
    }

}
