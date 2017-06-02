package it.unical.igpe.tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MapManager {
	public int[][] map;

	public MapManager(int width, int height) {
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
