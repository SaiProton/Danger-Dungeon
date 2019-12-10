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
        put("wh2", "wall_hole_2");
    }};

    private String[][][] floorPlan = {
            {
                    {"wbb.", "wbg.", "wbr.", "wby."},
                    {"f1..", "f2..", "f3..", "f4.."},
                    {"f5..", "f6..", "f7..", "f8.."}
            },
    };

    public Tile[][] floor;

    public int[] mapSpeed = new int[2];

    public Map(int level) {
        generateMap(level);
    }

    public void generateMap(int level) {
        floor = new Tile[floorPlan[level].length][floorPlan[level][0].length];

        for(int row = 0; row < floorPlan[level].length; row++) {
            for(int col = 0; col < floorPlan[level][row].length; col++) {
                String name = mapSymbs.get(floorPlan[level][row][col]);

                if(floorPlan[level][row][col].charAt(0) == 'f') {
                    floor[row][col] = new Floor(name, col * Tile.SIZE + 200, row * Tile.SIZE + 200, 0);
                } else if(floorPlan[level][row][col].charAt(0) == 'w') {
                    floor[row][col] = new Wall(name, col * Tile.SIZE + 200, row * Tile.SIZE + 200, 0);
                }

            }
        }
    }

    public int[] tileTouchPos(int touchX, int touchY) {
        for(Tile[] row : floor) {
            for(Tile t : row) {
                if(t instanceof Floor) {
                    if(((Floor) t).checkTouch(touchX, touchY)) {
                        return new int[]{t.tileX, t.tileY};
                    }
                }
            }
        }
        return null;
    }

    public void calcMapSpeed(int[] tileTouch, int heroX, int heroY, int heroSpd) {
        //this method is gonna be a lot of math
    }

    public void drawMap(Canvas canvas) {
        for(Tile[] row : floor) {
            for(Tile t : row) {
                t.draw(canvas);
            }
        }
    }
}
