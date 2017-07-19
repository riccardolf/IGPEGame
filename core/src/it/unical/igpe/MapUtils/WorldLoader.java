package it.unical.igpe.MapUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class WorldLoader {
	public int[][] map;

	public WorldLoader(int width, int height) {
		map = new int[height][width];
	}

	public void LoadMap(String path) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		for (int i = 0; i < 64; i++) {
			String line = null;
			line = br.readLine();
			String[] tokens = line.split(" ");
			for (int j = 0; j < tokens.length; j++) {
				this.map[j][i] = Integer.parseInt(tokens[j]);
			}
		}
		br.close();
	}
}
