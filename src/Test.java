
public class Test {
	
	public static void main(String[] args){
		int x = 8;
		for(int i=0;i<=9;i++){
			for(int j=0;j<=9;j++){
				for(int y=0;y<=9;y++){
					if(i*j*8*y==1152 && (i-j-8-y) == -12){
						System.out.println("i:"+i+"j:"+j+"x:"+8+"y:"+y +"  sum="+(i-j-8-y));
					}
				}
			}
		}
	}
}
