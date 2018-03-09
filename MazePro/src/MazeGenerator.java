import java.util.Collections;
import java.util.Arrays;
 

public class MazeGenerator {
	private final int x;
	private final int y;
	private final int[][] maze;
	private int[][] grid;
 
	public MazeGenerator(int x, int y) {
		this.x = x;
		this.y = y;
		maze = new int[this.x][this.y];
		grid = new int[this.x*2+1][this.y*2+1];
		generateMaze(0, 0);
		this.convertFormat();
	}

	private void convertFormat(){
		for (int i = 0; i < y; i++) {
			
			for (int j = 0; j < x; j++) {
				if((maze[j][i] & 1) == 0){
					grid[i*2][j*2] = 0;
					grid[i*2][j*2+1] = 0;
				}else {
					grid[i*2][j*2] = 0;
					grid[i*2][j*2+1] = 1;
				}
			}
			grid[i*2][grid[0].length-1] = 0;
			
			for (int j = 0; j < x; j++) {
				if((maze[j][i] & 8) == 0){
					grid[i*2+1][j*2] = 0;
					grid[i*2+1][j*2+1] = 1;
				}else {
					grid[i*2+1][j*2] = 1;
					grid[i*2+1][j*2+1] = 1;
				}
			}
			grid[i*2+1][grid[0].length-1] = 0;
		}
		
		for (int j = 0; j < x; j++) {
			grid[grid.length-1][j*2] = 0;
			grid[grid.length-1][j*2+1] = 0;
		}
		grid[grid.length-1][grid[0].length-1] = 0;
	}

	public int[][] getMaze(){
		return grid;
	}
	
	private void generateMaze(int cx, int cy) {
		DIR[] dirs = DIR.values();
		Collections.shuffle(Arrays.asList(dirs));
		for (DIR dir : dirs) {
			int nx = cx + dir.dx;
			int ny = cy + dir.dy;
			if (between(nx, x) && between(ny, y)
					&& (maze[nx][ny] == 0)) {
				maze[cx][cy] |= dir.bit;
				maze[nx][ny] |= dir.opposite.bit;
				generateMaze(nx, ny);
			}
		}
	}
 
	private static boolean between(int v, int upper) {
		return (v >= 0) && (v < upper);
	}
 
	private enum DIR {
		N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);
		private final int bit;
		private final int dx;
		private final int dy;
		private DIR opposite;
 
		static {
			N.opposite = S;
			S.opposite = N;
			E.opposite = W;
			W.opposite = E;
		}
 
		private DIR(int bit, int dx, int dy) {
			this.bit = bit;
			this.dx = dx;
			this.dy = dy;
		}
	};
 
}