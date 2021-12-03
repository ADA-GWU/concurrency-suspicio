public class MultiThread extends Thread {

    private int x0, x1, y0, y1;

    MultiThread (int x0, int y0, int x1, int y1) {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
    }

    @Override
    public void run() {
        int y0;
        int x0 = this.x0;

        while (x0 < x1) {
            int x1 = x0 + Main.getChunkSize();
            int y1 = this.y0 + Main.getChunkSize();
            y0 = this.y0;
            while (y0 < this.y1) {
                Main.setColor(x0, y0, Math.min(x1, this.x1), Math.min(y1, this.y1), Main.getColor(x0, y0, Math.min(x1, this.x1), Math.min(y1, this.y1)));
                Main.rerender();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
                y0 += Main.getChunkSize();
                y1 += Main.getChunkSize();
            }
            x0 += Main.getChunkSize();
            System.out.println(x0);
        }
    }

    public static void runInParallel(String picName) {
        System.out.println("Output");
        Main.setPicture(new Picture(picName));
        Main.createWindow();
        System.out.println("Output");

        int coresCount = Runtime.getRuntime().availableProcessors();
        Integer chunkSize = Main.getChunkSize();
        int chunks = Main.getPicture().height() / chunkSize;
        int chunksPerThread = chunks / (coresCount - 1);

        Main.InitPictureInFrame();
        MultiThread[] multiThreads = new MultiThread[coresCount];
        System.out.println("Output");
        for (int i = 0; i < multiThreads.length; i++) {
            multiThreads[i] = new MultiThread(chunksPerThread * i, chunksPerThread * i, chunksPerThread * (i + 1), chunksPerThread * (i + 1));
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
