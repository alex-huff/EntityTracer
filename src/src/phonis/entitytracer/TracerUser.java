package src.phonis.entitytracer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.UUID;

import org.bukkit.Location;

public class TracerUser {
	private UUID _uuid;
	private Location _loc;
	private boolean _traceEnabled;
	private int _maxDistance;
	private int _minTravelDistance;
	private int _maxParticles;
	private int _traceTime;
	private double _viewRadius;
	private boolean _unlimitedRadius;
	private HashSet<ParticleLocation> _particleLocations;
	private boolean _sandExplosionEnabled;
	private boolean _TNTExplosionEnabled;
	private boolean _tickConnect;
	
    public TracerUser(UUID uuid, Location loc) {
    	this._uuid = uuid;
    	this._loc = loc;
    	this._traceEnabled = false;
    	this._maxDistance = 100;
    	this._particleLocations = new HashSet<ParticleLocation>();
    	this._minTravelDistance = 1;
    	this._maxParticles = 1000;
    	this._traceTime = 400;
    	this._viewRadius = 0;
    	this._unlimitedRadius = true;
    	this._sandExplosionEnabled = false;
    	this._TNTExplosionEnabled = true;
    	this._tickConnect = true;
    }
    
    public UUID getUUID() {
    	return this._uuid;
    }
    
    public Location getLocation() {
    	return this._loc;
    }
    
    public boolean getTraceEnabled() {
    	return this._traceEnabled;
    }
    
    public int getMaxDistance() {
    	return this._maxDistance;
    }
    
    public HashSet<ParticleLocation> getParticleLocations() {
    	return this._particleLocations;
    }
    
    public int getMinTravelDistance() {
    	return this._minTravelDistance;
    }
    
    public int getMaxParticles() {
    	return this._maxParticles;
    }
    
    public int getTraceTime() {
    	return this._traceTime;
    }
    
    public double getViewRadius() {
    	return this._viewRadius;
    }
    
    public boolean isUnlimitedRadius() {
    	return this._unlimitedRadius;
    }
    
    public boolean isSandExplosionEnabled() {
    	return this._sandExplosionEnabled;
    }
    
    public boolean isTNTExplosionEnabled() {
    	return this._TNTExplosionEnabled;
    }
    
    public boolean isTickConnect() {
    	return this._tickConnect;
    }
    
    public void setSandExplosionEnabled(boolean sandExplosionEnabled) {
    	this._sandExplosionEnabled = sandExplosionEnabled;
    }
    
    public void setTNTExplosionEnabled(boolean TNTExplosionEnabled) {
    	this._TNTExplosionEnabled = TNTExplosionEnabled;
    }
    
    public void setUUID(UUID uuid) {
    	this._uuid = uuid;
    }
    
    public void setLocation(Location loc) {
    	this._loc = loc;
    }
    
    public void setTraceEnabled(boolean traceEnabled) {
    	this._traceEnabled = traceEnabled;
    }
    
    public void setMaxDistance(int maxDistance) {
    	this._maxDistance = maxDistance;
    }
    
    public void setMinTravelDistance(int minTravelDistance) {
    	this._minTravelDistance = minTravelDistance;
    }
    
    public void setMaxParticles(int maxParticles) {
    	this._maxParticles = maxParticles;
    }
    
    public void addParticleLocation(ParticleLocation location) {
    	if(this._particleLocations.size() != 0 && !(location.getLocation().getWorld() == this._particleLocations.iterator().next().getLocation().getWorld())) {
    		this._particleLocations.clear();
    	}
    	
    	this._particleLocations.remove(location);	
    	this._particleLocations.add(location);
    }
    
    public void setTraceTime(int traceTime) {
    	this._traceTime = traceTime;
    }
    
    public void setTickConnect(boolean tickConnect) {
    	this._tickConnect = tickConnect;
    }
    
    public void clearParticleLocations() {
    	this._particleLocations.clear();
    }
    
    public void updateRadius(Location pLoc)
	{
    	if(this._particleLocations.size() != 0 && !(pLoc.getWorld() == this._particleLocations.iterator().next().getLocation().getWorld())) {
    		this._particleLocations.clear();
    		
    		return;
    	}
    	if(this._maxParticles > this._particleLocations.size()) {
    		this._unlimitedRadius = true;
    	}else {
    		this._unlimitedRadius = false;
    		ArrayList<ParticleLocation> pLAL = new ArrayList<ParticleLocation>(this._particleLocations);
    		PriorityQueue<ParticleLocation> pq = new PriorityQueue<ParticleLocation>((new ParticleLocationComparator(pLoc)).reversed());
    		pq.addAll(pLAL.subList(0, this._maxParticles));

    		for (int i = this._maxParticles; i < this._particleLocations.size(); i++) {
    			if (pq.comparator().compare(pLAL.get(i), pq.peek()) == 1) {
    				pq.poll();
    				pq.add(pLAL.get(i));
    			}
    		}
    		
    		this._viewRadius = pLoc.distance(pq.peek().getLocation());
    	}
	}
}