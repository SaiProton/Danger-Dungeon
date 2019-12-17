package com.shayanr.dangerdungeon.gameplay.mapping;

import android.graphics.Canvas;

import java.util.HashMap;

public class Map {
    private static final HashMap<String, String> mapSymbs = new HashMap<String, String>()
    {{
        //flooring
        for(int i = 1; i < 9; i++) {
            put("f" + i + "..", "floor_" + i);
        }

        put("fl..", "floor_ladder");
        put("fs..", "floor_spikes_anim_f");
        put("fh..", "hole");

        //banners
        put("wbb.", "wall_banner_blue");
        put("wbg.", "wall_banner_green");
        put("wbr.", "wall_banner_red");
        put("wby.", "wall_banner_yellow");

        //columns
        put("wcm.", "wall_column_mid");
        put("wct.", "wall_column_top");
        put("wcb.", "wall_column_base");

        //corners
        put("wcbl", "wall_corner_bottom_left");
        put("wcbr", "wall_corner_bottom_right");
        put("wcfl", "wall_corner_front_left");
        put("wcfr", "wall_corner_front_right");
        put("wcl.", "wall_corner_left");
        put("wcr.", "wall_corner_right");
        put("wctl", "wall_corner_top_left");
        put("wctr", "wall_corner_top_right");

        //fountains
        put("wfbb", "wall_fountain_basin_blue_anim_f");
        put("wfbr", "wall_fountain_basin_red_anim_f");
        put("wfmb", "wall_fountain_mid_blue_anim_f");
        put("wfmr", "wall_fountain_mid_red_anim_f");
        put("wft.", "wall_fountain_top");

        //goo
        put("wg..", "wall_goo");
        put("wgb.", "wall_goo_base");

        //holes
        put("wh1.", "wall_hole_1");
        put("wh2.", "wall_hole_2");

        //normalwalls
        put("wl..", "wall_left");
        put("wm..", "wall_mid");
        put("wr..", "wall_right");
    }};

    private String[][][] floorPlan = {
            {
                    {"....", "....", "....", "....", "wct.", "....", "....", "....", "....", "....", "....", "...."},
                    {"wbb.", "wbg.", "wbr.", "wby.", "wcm.", "wg..", "wfmb", "wfmr", "wh1.", "wh2.", "wm..", "wr.."},
                    {"f1..", "f2..", "f3..", "f4..", "wcb.", "wgb.", "wfbb", "wfbr", "fs..", "fs..", "fh..", "fl.."},
                    {"f5..", "f6..", "f7..", "f8..", "f5..", "f6..", "f7..", "f8..", "f5..", "f6..", "f7..", "f8.."},
                    {"f1..", "f2..", "f3..", "f4..", "f1..", "f2..", "f3..", "f4..", "f5..", "f6..", "f7..", "f8.."},
                    {"f5..", "f6..", "f7..", "f8..", "f5..", "f6..", "f7..", "f8..", "f5..", "f6..", "f7..", "f8.."},
                    {"f1..", "f2..", "f3..", "f4..", "f1..", "f2..", "f3..", "f4..", "f5..", "f6..", "f7..", "f8.."},
                    {"f5..", "f6..", "f7..", "f8..", "f5..", "f6..", "f7..", "f8..", "f5..", "f6..", "f7..", "f8.."}
            },
    };

    public Tile[][] floor;

    public double[] mapSpeed = new double[2];
    private double[] subSpeed = {0, 0};
    private double[] relaPos = new double[2];

    public Map(int level) {
        generateMap(level);
    }

    public void generateMap(int level) {
        floor = new Tile[floorPlan[level].length][floorPlan[level][0].length];

        for(int row = 0; row < floorPlan[level].length; row++) {
            for(int col = 0; col < floorPlan[level][row].length; col++) {
                String name = mapSymbs.get(floorPlan[level][row][col]);
                int frames = 0;
                if(floorPlan[level][row][col].charAt(0) == 'f') {
                    if(floorPlan[level][row][col].charAt(1) == 's') {
                        frames = 3;
                    }
                    floor[row][col] = new Floor(name, col * Tile.SIZE + 200, row * Tile.SIZE + 200, frames);
                } else if(floorPlan[level][row][col].charAt(0) == 'w') {
                    if(floorPlan[level][row][col].charAt(1) == 'f') {
                        frames = 3;
                    }

                    floor[row][col] = new Wall(name, col * Tile.SIZE + 200, row * Tile.SIZE + 200, frames);
                }

            }
        }
    }

    public int[] tileTouchPos(int touchX, int touchY) {
        for(Tile[] row : floor) {
            for(Tile t : row) {
                if(t instanceof Floor) {
                    if(((Floor) t).checkTouch(touchX, touchY)) {
                        int[] tiTouch = {t.tileX, t.tileY};
                        return tiTouch;
                    }
                }
            }
        }
        return null;
    }

    public void calcMapSpeed(int[] tileTouch, int[] heroPos, int heroSpd) {
        relaPos[0] = (tileTouch[0] - heroPos[0]) * -1;
        relaPos[1] = (tileTouch[1] - heroPos[1]) * -1;

        double distance = getDist(relaPos[0], relaPos[1]);
        System.out.println(distance);
        for(int i = 0; i < 2; i++) {
            mapSpeed[i] = ((relaPos[i] / distance) * heroSpd);
            subSpeed[i] = 0;
        }
    }

    public void update() {
        if(getDist(relaPos[0], relaPos[1]) <= getDist(mapSpeed[0], mapSpeed[1])) {
            moveMap(relaPos);
            for(int i = 0; i < 2; i++) {
                relaPos[i] = 0;
                mapSpeed[i] = 0;
                subSpeed[i] = 0;
            }
        } else {
            moveMap(mapSpeed);
            for(int i = 0; i < 2; i++) {
                relaPos[i] -= (int)Math.floor(mapSpeed[i]);
                subSpeed[i] += mapSpeed[i] - (int)Math.floor(mapSpeed[i]);
                if (Math.abs(subSpeed[i]) >= 1) {
                    moveMap(subSpeed);
                    relaPos[i] -= Math.floor(subSpeed[i]);
                    subSpeed[i] -= Math.floor(subSpeed[i]);
                }
            }
        }
    }

    private void moveMap(double[] speed) {
        for(Tile[] row : floor) {
            for(Tile t : row) {
                if(t != null) {
                    t.move((int) Math.floor(speed[0]), (int) Math.floor(speed[1]));
                }
            }
        }
    }

    public void drawMap(Canvas canvas) {
        for(Tile[] row : floor) {
            for(Tile t : row) {
                if(t != null) {
                    t.draw(canvas);
                }
            }
        }
    }

    private double getDist(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }
}
