package org.example.ag;

import lombok.RequiredArgsConstructor;
import org.example.ag.AgSettings;

import java.util.logging.Logger;

@RequiredArgsConstructor
public class AgThread extends Thread {

   final AgSettings agSettings;

    @Override
    public void run() {
        Logger.getGlobal().info("Ag starts");
//        for(int i=0; i<agSettings.generationsNumber;i++)
    }
}
