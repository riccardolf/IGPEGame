package it.unical.igpe.tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class WorldLoader {
	public int[][] map;
	
	public WorldLoader (int[][] existingMap) {
		map = new int[existingMap.length][existingMap[0].length];
		
		for (int x = 0; x < map.length; x++)
			for (int y = 0; y < map.length; y++)
				map[x][y] = existingMap[x][y];
	}
	
	public WorldLoader(int width, int height) {
		map = new int[height][width];
	}
	
	public static WorldLoader FromFile(String fileHandle) throws IOException {
		WorldLoader layer = null;
		
		ArrayList<ArrayList<Integer>> tempLayout = new ArrayList<ArrayList<Integer>>();
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(fileHandle));
			
		String currentLine;
			
		while((currentLine = br.readLine()) != null) {
			if(currentLine.isEmpty())
				continue;
			ArrayList<Integer> row = new ArrayList<Integer>();
			
			String[] values = currentLine.trim().split(" ");
			
			for(String string:values)
				if(!string.isEmpty()) {
					int id = Integer.parseInt(string);
					
					row.add(id);
				}
			
			tempLayout.add(row);
		}
		
		int width = tempLayout.get(0).size();
		int height = tempLayout.size();
		
		layer = new WorldLoader(width, height);
		
		for(int x = 0; x < height; x++) {
			for(int y = 0; y < width; y++) {
				layer.map[x][y] = tempLayout.get(x).get(y);
			}
		}
		
		return layer;
	}	
}