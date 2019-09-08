package uk.ashigaru.engine.math;

import java.util.ArrayList;
import java.util.List;

public class QuadTree<Element extends SpatialElement> implements SpatialElement {

	public static final int QT_NODE_CAPACITY = 4;
	
	public AABB boundary;
	
	public List<Element> points = new ArrayList<Element>(QT_NODE_CAPACITY);
	
	public QuadTree<Element> northWest;
	public QuadTree<Element> northEast;
	public QuadTree<Element> southWest;
	public QuadTree<Element> southEast;
	
	public QuadTree(AABB boundary) {
		this.boundary = boundary;
	}
	
	public void clear() {
		points.clear();
		if(northWest == null) return;
		northWest.clear();
		northEast.clear();
		southWest.clear();
		southEast.clear();
	}
	
	public boolean insert(Element qte) {
		if(!boundary.contains(qte.getX(), qte.getY())) return false;
		
		if(points.size() < QT_NODE_CAPACITY && northWest == null) {
			points.add(qte);
			return true;
		}
		
		if(northWest == null) subdivide();
		
		if(northWest.insert(qte)) return true;
		if(northEast.insert(qte)) return true;
		if(southWest.insert(qte)) return true;
		if(southEast.insert(qte)) return true;
		
		return false;
	}
	
	public void subdivide() {
		northWest = new QuadTree<Element>(new AABB(boundary.x - boundary.halfD/2f, boundary.y - boundary.halfD/2f, boundary.halfD/2f));
		northEast = new QuadTree<Element>(new AABB(boundary.x + boundary.halfD/2f, boundary.y - boundary.halfD/2f, boundary.halfD/2f));
		southWest = new QuadTree<Element>(new AABB(boundary.x - boundary.halfD/2f, boundary.y + boundary.halfD/2f, boundary.halfD/2f));
		southEast = new QuadTree<Element>(new AABB(boundary.x + boundary.halfD/2f, boundary.y + boundary.halfD/2f, boundary.halfD/2f));
	}
	
	public List<Element> queryRange(AABB range) {
		List<Element> pointsInRange = new ArrayList<Element>();
		if(!boundary.intersects(range)) {
			return pointsInRange;
		}
		
		for(int p = 0; p < points.size(); p++) {
			if(range.contains(points.get(p).getX(), points.get(p).getY())) {
				pointsInRange.add(points.get(p));
			}
		}
		
		if(northWest == null) return pointsInRange;
		
		pointsInRange.addAll(northWest.queryRange(range));
		pointsInRange.addAll(northEast.queryRange(range));
		pointsInRange.addAll(southWest.queryRange(range));
		pointsInRange.addAll(southEast.queryRange(range));
		return pointsInRange;
	}
	
	public void remove(Element element) {
		if(points.contains(element)) {
			points.remove(element);
		} else {
			if(northWest == null) return;
			northWest.remove(element);
			northEast.remove(element);
			southWest.remove(element);
			southEast.remove(element);
		}
	}

	@Override
	public float getX() {
		return boundary.y;
	}

	@Override
	public float getY() {
		return boundary.y;
	}

	@Override
	public float getRadius() {
		return boundary.halfD;
	}
	
	
}
