//multi thread class to run in parallel
public class MultiThread extends Thread {

    //accepts blocks of coordinates that are left upper coordinates of block to repaint
    private int[][] blocks;

    //constructor
    MultiThread(int[][] blocks) {
        this.blocks = blocks;
    }

    @Override
    //run is function where thread repaint images
    public void run() {
        //goes through all blocks and repaint them
        for (int i = 0; i < blocks.length; i++) {
            int x0 = blocks[i][0];
            int y0 = blocks[i][1];
            int x1 = x0 + Main.getChunkSize();
            int y1 = y0 + Main.getChunkSize();

            Main.setColor(x0, y0, x1, y1, Main.getColor(x0, y0, x1, y1));
            Main.rerender();
            //additional pause to see the changes
            try {
                Thread.sleep(10);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    public static void runInParallel(String picName) {
        //init
        Main.setPicture(new Picture(picName));
        Main.createWindow();

        //calculates the numbers of processors that are free, in my case 12
        int coresCount = Runtime.getRuntime().availableProcessors();

        Main.InitPictureInFrame();
        //if amount is too much decreases them
        if (coresCount > Main.getChunks())
            coresCount = Main.getChunks();
        //creates the threads instances
        MultiThread[] multiThreads = new MultiThread[coresCount];
        //calculate the amount of chunks pre thread. +1 needed to fill edge cases
        int chunksPerThread = Main.getChunks() / multiThreads.length + 1;

        //i => multiThread array length
        //j => width
        //t => height
        for (int i = 0, j = 0, t = 0; i < multiThreads.length; i++) {
            int[][] chunks = new int[chunksPerThread][2];
            for (int k = 0; k < chunksPerThread; k++) {
                //calculates the blocks coordinates
                chunks[k][1] = t * Main.getChunkSize();
                chunks[k][0] = j * Main.getChunkSize();
                t++;

                if (t > Main.getHeightChunks()) {
                    t = 0;
                    j++;
                }
            }
            multiThreads[i] = new MultiThread(chunks);
        }


        //starts multi thread process
        for (int i = 0; i < multiThreads.length; i++)
            multiThreads[i].start();

        try {//waits till all threads are done
            for (int i = 0; i < multiThreads.length; i++)
                multiThreads[i].join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //saves image
        Main.save();
    }
}
