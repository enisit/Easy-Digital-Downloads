package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;


public class IP2Location {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		String fileName="IPAdress.CSV";
		File file=new File(fileName);
		System.out.println("Enter IP adress: ");
		Scanner in = new Scanner(System.in);
		String adress=in.nextLine();
		
		RandomAccessFile red=new RandomAccessFile(file,"r");
		long high=red.length();
		red.readLine();
		long low=red.getFilePointer();
		long number=IP2Number(adress);
		long mid=low+(high-low)/2;
		
		long start=System.nanoTime();
		try {
			
			while(high>low)
			{
				
				red.seek(mid);
				String line=red.readLine();
				//long pointer=red.getFilePointer();
				line=red.readLine();
				String[] values=line.split(",");
				long StartIP=Long.parseLong(values[0].replace("\"", ""));
				long EndIP=Long.parseLong(values[1].replace("\"", ""));
				if(number<StartIP)
				{
					high=mid;
					mid=low+(high-low)/2;
					
				}
				else if(number>EndIP)
				{
					low=mid;
					mid=low+(high-low)/2;
					
				}
				else if((number>StartIP)||(number<EndIP))
				{
					if(values[5]=="\"-\"")
					{
						System.out.println("This IP Adress does not exist");
						break;
					}
					else
					{
						System.out.println("County: "+values[3].replace("\"", ""));
						System.out.println("Region: "+values[4].replace("\"", ""));
						System.out.println("City: "+values[5].replace("\"", ""));
						
						break;
					}
				}
				else
				{	
					System.out.println("This IP Adress does not exist");
				}
				
			}
			
			red.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long end=System.nanoTime();
		long total=end/1000-start/1000;
		System.out.println("Time elapsed: "+(total/1000)+" miliseconds");
	}

	
	public static  long IP2Number(String adress)
	{
		

		long IPNumber;
		
		String[] values=adress.split("\\.");
		long w=16777216*Long.parseLong(values[0]);
		long x= 65536*Long.parseLong(values[1]);
		long y= 256*Long.parseLong(values[2]);
		long z=Long.parseLong(values[3]);
		IPNumber=w+x+y+z;
		
		
		return IPNumber;
	}
}
