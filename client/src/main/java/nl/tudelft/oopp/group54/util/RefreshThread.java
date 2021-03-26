package nl.tudelft.oopp.group54.util;

import nl.tudelft.oopp.group54.controllers.LectureRoomSceneController;

public class RefreshThread implements Runnable {
    private LectureRoomSceneController lecture;

    public RefreshThread(LectureRoomSceneController lecture) {
        this.lecture = lecture;
    }

    @Override
    public void run() {
        do {
            long var = System.currentTimeMillis() / 1000;
            double var2 = var % 10;

            if (var2 == 1.0) {
                this.lecture.refreshButtonClickedAfter();
            }

        } while (this.lecture.isLectureEnded() == false);

        System.out.println("Goodbye!");
    }
}
