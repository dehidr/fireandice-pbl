public class GameObject {
    enum Type{
        SCORE1,
        SCORE2,
        SCORE3,
        PACKEDICE,
        FIRE,
        ICE,
        NPC
    }

    public void set(GameObject obj){
        this.type = obj.type;
        this.coordinate.set(obj.coordinate);
    }

    protected Coordinate coordinate;
    Type type;

    public GameObject(){}

    public GameObject(Type t){
        this.type = t;
        this.coordinate = GameField.getInstance().getBlank();
    }

    public GameObject(Coordinate c, Type t){
        this.coordinate = c;
        this.type=t;
    }

    public Coordinate getCoordinate() { return coordinate; }
    public void setCoordinate(Coordinate coordinate) { this.coordinate = new Coordinate(coordinate);}

    public int getX() { return coordinate.getX(); }
    public int getY() { return coordinate.getY(); }

    public void setX(int x) { coordinate.setX(x); }
    public void setY(int y) { coordinate.setY(y); }

    public Type getType() { return type; }
    public void setType(Type type) { this.type = type; }

    public String toString(){
        try{
            switch (type){
                case NPC -> {return "C";}
                case FIRE -> {return "-";}
                case PACKEDICE -> {return "@";}
                case SCORE1 -> {return "1";}
                case SCORE2 -> {return "2";}
                case SCORE3 -> {return "3";}
            }
        }catch (Exception e){
        }
        return "";
    }
}
