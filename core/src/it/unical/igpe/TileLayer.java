package it.unical.igpe;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TileLayer {
	public int[][] map;
	
	public TileLayer (int[][] existingMap) {
		map = new int[existingMap.length][existingMap[0].length];
		
		for (int y = 0; y < map.length; y++)
			for (int x = 0; y < map[y].length; y++)
				map[y][x] = existingMap[y][x];
	}
	
	public TileLayer(int width, int height) {
		map = new int[height][width];
	}
	
	public static TileLayer FromFile(String fileHandle) throws IOException {
		TileLayer layer = null;
		
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
		
		layer = new TileLayer(width, height);
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				layer.map[y][x] = tempLayout.get(y).get(x);
			}
		}
		
		return layer;
	}	
}
