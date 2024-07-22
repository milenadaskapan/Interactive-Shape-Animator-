import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.util.*;


public class NestedShape extends RectangleShape {
    private ArrayList<Shape> innerShapes=new ArrayList<Shape>();
    
    public NestedShape() {
        super();
        Shape a = createInnerShape(0, 0, getWidth() / 2, getHeight() / 2, color, borderColor, PathType.BOUNCING, ShapeType.RECTANGLE);
        int l = Integer.parseInt(getLabel()) + 1;
        a.setLabel(Integer.toString(l));
    }

    public NestedShape(int x, int y, int w, int h, int pw, int ph, Color c, Color bc, PathType pt) {
        super(x, y, w, h, pw, ph, c, bc, pt);
        Shape a = createInnerShape(0, 0, getWidth() / 2, getHeight() / 2, c,bc, PathType.BOUNCING, ShapeType.RECTANGLE);
        int l = Integer.parseInt(getLabel()) + 1;
        a.setLabel(Integer.toString(l));
    }

    public NestedShape(int w, int h) {
        super(0, 0, w, h, DEFAULT_PANEL_WIDTH, DEFAULT_PANEL_HEIGHT, Color.black, Color.black, PathType.BOUNCING);
    }

    public Shape createInnerShape(int x, int y, int w, int h, Color c, Color bc, PathType pt, ShapeType st) {
        Shape s;
        if (st == ShapeType.RECTANGLE) {
            s = new RectangleShape(x, y, w, h, getWidth(), getHeight(), c, bc, pt);
        } else if (st == ShapeType.OVAL) {
            s = new OvalShape(x, y, w, h, getWidth(), getHeight(), c, bc, pt);
        } else {
            s = new NestedShape(x, y, w, h, getWidth(), getHeight(), c, bc, pt);
        }
        innerShapes.add(s);
        s.setParent(this);
        return s;
    }
    
    public Shape createInnerShape(PathType pt, ShapeType st) {
        Shape s;
        if (st == ShapeType.RECTANGLE) { s= new RectangleShape(0,0,(width/2), (height/2), width, height, color, borderColor, pt);}
        else if (st==ShapeType.OVAL) { s = new OvalShape(0,0,(width/2), (height/2), width, height, color, borderColor, pt);}
        else { s = new NestedShape(0,0,(width/2), (height/2), width, height, color, borderColor, pt);}
        innerShapes.add(s);
        s.setParent(this);
        return s;
    }
    
    public Shape getInnerShapeAt(int index) {return innerShapes.get(index);}
    public int getSize() {return innerShapes.size();}
    
    public void draw(Graphics g) {
    g.setColor(Color.black);
    g.drawRect(x,y,width,height);
    g.translate(x,y);
    for(Shape s: innerShapes){
            s.draw(g);
			s.drawHandles(g);
			s.drawString(g);
        }
    
    g.translate(-x,-y);
    
    }

    public void move() {
        super.move();
        for(Shape s: innerShapes){
            s.move();
            }
    }
    
    public int indexOf(Shape s) {return innerShapes.indexOf(s);}
    public void addInnerShape(Shape s) {
        innerShapes.add(s);
        s.setParent(this);
    }
    
    public void removeInnerShape(Shape s) {
        innerShapes.remove(s);
        s.setParent(null);
    }
    
    public void removeInnerShapeAt(int index) {
        Shape s = innerShapes.get(index);
        innerShapes.remove(s);
        s.setParent(null);
    }
    
    public ArrayList<Shape> getAllInnerShapes() {return innerShapes;}
}