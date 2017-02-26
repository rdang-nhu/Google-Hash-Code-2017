import java.util.*;

public class Knapsack {
    
    public static List<Video> solve(Server s) {

        List<Video> videos = s.allVideos();
        int size_x = videos.size() + 1;
        int size_y = s.capacity + 1;
        float[][] m = new float[size_x][size_y];
        Video[][] added_video = new Video[size_x][size_y];
        int[][] previous_size = new int[size_x][size_y];

        List<Video> l = new LinkedList<Video>();
        int i = 1;
        for (Video v : videos) {
            
            for (int j = 0; j < size_y; j++) {
                if (v.size > j) {
                    m[i][j] = m[i - 1][j];
                    previous_size[i][j] = j;
                }
                else {
                    float score = m[i - 1][j - v.size] + v.scoreServer(s);
                    if (score > m[i - 1][j]) {
                        m[i][j] = score;
                        added_video[i][j] = v;
                    }
                    else {
                        m[i][j] = m[i - 1][j];
                    }
                }
            }
            i++;
        }
        
        int w = size_y - 1;
        for (int n = size_x - 1; n > 0; n--) {
            Video v = added_video[n][w];
            if (v != null) {
                l.add(v);
                w -= v.size;
            }
        }
        return l;
    }

}
