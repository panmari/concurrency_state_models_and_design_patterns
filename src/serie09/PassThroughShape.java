package serie09;

public class PassThroughShape implements Shape {

    private final PositionHelper positionHelper;
    private final DimensionHelper dimensionHelper;

    public PassThroughShape() {
        this.positionHelper = new PositionHelper(10, 10);
        this.dimensionHelper = new serie09.PassThroughShape.DimensionHelper(5, 5);
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
        // TODO
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
}
