public class MultiThread extends Thread {

    private int[][] blocks;

    MultiThread(int[][] blocks) {
        this.blocks = blocks;
    }

    @Override
    public void run() {
        for (int i = 0; i < blocks.length; i++) {
            int x0 = blocks[i][0];
            int y0 = blocks[i][1];
            int x1 = x0 + Main.getChunkSize();
            int y1 = y0 + Main.getChunkSize();

            Main.setColor(x0, y0, x1, y1, Main.getColor(x0, y0, x1, y1));
            Main.rerender();
            try {
                Thread.sleep(10);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    public static void runInParallel(String picName) {
        Main.setPicture(new Picture(picName));
        Main.createWindow();

        int coresCount = Runtime.getRuntime().availableProcessors();
        Integer chunkSize = Main.getChunkSize();

        Main.InitPictureInFrame();
        if (coresCount > Main.getChunks())
            coresCount = Main.getChunks();
        MultiThread[] multiThreads = new MultiThread[coresCount];
        int chunksPerThread = Main.getChunks() / multiThreads.length + 1;

        for (int i = 0, j = 0, t = 0; i < multiThreads.length; i++) {
            int[][] chunks = new int[chunksPerThread][2];
            for (int k = 0; k < chunksPerThread; k++) {
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


        for (int i = 0; i < multiThreads.length; i++)
            multiThreads[i].start();

        try {
            for (int i = 0; i < multiThreads.length; i++)
                multiThreads[i].join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Main.save();
    }
}
