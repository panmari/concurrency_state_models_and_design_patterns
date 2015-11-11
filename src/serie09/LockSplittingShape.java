package serie09;

public class LockSplittingShape implements Shape {
	private Object dimensionLock = new Object();
	private Object positionLock = new Object();
	private float width=10;
	private float height=10;
	private float x=5;
	private float y=5;
	
	@Override
	public void changePosition() {
		synchronized(positionLock)
		{
			x *= 1.1;
			y /= 1.2;
		}
	}

	@Override
	public void changeDimension() {
		synchronized(dimensionLock)
		{
			width*=1.5;
			height/=1.8;
		}
	}

	@Override
	public void changePositionAndDimension() {
		synchronized(positionLock)
		{
			synchronized(dimensionLock)
			{
				y *= 1.4;
				height /= 1.6;
			}
		}
	}

}
