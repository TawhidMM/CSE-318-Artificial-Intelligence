import java.util.Arrays;

public class Node {
    private final int[] config;
    private final Side side;
    private final int capturedGem;
    private final int extraMove;

    public Node(int[] config, Side side,
                int extraMove, int oppositeCaptured) {

        this.config = config;
        this.side = side;
        this.extraMove = extraMove;
        this.capturedGem = oppositeCaptured;
    }

    int getCapturedGem() {
        return capturedGem;
    }
    int getGem(int bin) {
        return config[bin];
    }
    boolean binEmpty(int bin){
        return this.config[bin] == 0;
    }
    public int[] getConfigCopy() {
        return Arrays.copyOf(config, config.length);
    }
    boolean maxPlayer() {
        return side == Side.MAX;
    }
    Side getSide(){
        return side;
    }
    Side getToggledSide(){
        if(maxPlayer())
            return Side.MIN;

        return Side.MAX;
    }
    int extraMove(){
        return extraMove;
    }

}

enum Side {
    MIN,
    MAX
}
