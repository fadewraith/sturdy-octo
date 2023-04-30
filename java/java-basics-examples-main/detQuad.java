// Write a java program to accept a coordinate point in a XY coordinate system and determine its quadrant.

import java.util.*;

class detQuad{
	
	public static void main(String [] args){
		
		int x,y;
		
		System.out.print("Enter the coordinates of x and y: ");
		
		Scanner sc = new Scanner(System.in);
		x = sc.nextInt();
		y = sc.nextInt();
		
		if(x==0 && y==0)
			System.out.println("Coordinates are at origin");
		else if(x>0 && y>0)
			System.out.println("Coordinates are in I quadrant");
		else if(x<0 && y>0)
			System.out.println("Coordinates are in II quadrant");
		else if(x<0 && y<0)
			System.out.println("Coordinates are in III quadrant");
		else
			System.out.println("Coordinates are in IV quadrant");
		
		}
		
		}
