package coolguy.maven;

public class SuperRabbitObject extends RabbitObject {

	public SuperRabbitObject(int moveNum) {
		super(moveNum);
		// TODO Auto-generated constructor stub
	}
	public SuperRabbitObject(SuperRabbitObject femaleParent, SuperRabbitObject maleParent,int moveNum) {
		super(femaleParent, maleParent,moveNum);
	}
	//only unique method is this
	@Override public int generateDirection(int x ,int y, MapTile[][] map) {
		return 1;
	}
}
