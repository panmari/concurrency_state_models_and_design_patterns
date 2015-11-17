package serie09;

public class PassThroughShape implements Shape {

    private final PositionHelper positionHelper;
    private final DimensionHelper dimensionHelper;
    private final OverallStateHelper overallStateHelper;

    public PassThroughShape() {
        this.positionHelper = new PositionHelper(10, 10);
        this.dimensionHelper = new serie09.PassThroughShape.DimensionHelper(5, 5);
        this.overallStateHelper = new OverallStateHelper(positionHelper, dimensionHelper);
    }

    @Override
    public void changePosition() {
        positionHelper.moveRelative(1.1f, 0.8f);
    }

    @Override
    public void changeDimension() {
        dimensionHelper.scale(1.5f, 0.2f);
    }

    @Override
    public void changePositionAndDimension() {
        overallStateHelper.scaleAndMove(1.f, 0.4f, 1.f, 1.4f);
    }

    /**
     * Keeps track of position.
     */
    class DimensionHelper {
        private float width;
        private float height;

        DimensionHelper(int width, int height) {
            this.width = width;
            this.height = height;
        }

        private synchronized void scale(float widthScale, float heightScale) {
            width *= widthScale;
            height *= heightScale;
        }
    }

    /**
     * Keeps track of dimensions.
     */
    class PositionHelper {
        private float x;
        private float y;

        PositionHelper(int x, int y) {
            this.x = x;
            this.y = y;
        }

        private synchronized void moveRelative(float xScale, float yScale) {
            x *= xScale;
            y *= yScale;
        }
    }
    
    class OverallStateHelper {
        private final DimensionHelper dimensionHelper;
        private final PositionHelper positionHelper;

        OverallStateHelper(PositionHelper ph, DimensionHelper dh) {
            this.dimensionHelper = dh;
            this.positionHelper = ph;
        }

        private void scaleAndMove(float widthScale, float heightScale, float xScale, float yScale) {
            // Very similar to lock splitting case. Locks are acquired twice,
            // but this is no problem since java uses reentrant locks.
            synchronized (dimensionHelper) {
                synchronized (positionHelper) {
                    dimensionHelper.scale(widthScale, heightScale);
                    positionHelper.moveRelative(xScale, yScale);
                }
            }
        }
    }
}
