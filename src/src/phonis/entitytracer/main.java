package src.phonis.entitytracer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class main extends JavaPlugin implements CommandExecutor {
	private ArrayList<TracerUser> _users;
	private static HashSet<LocationChange> _changes;
	private Hashtable<Integer, EntityLocation> entityLocations;
	private int tickCount;
	private ArrayList<Offset> vertices;
	
    public static void addChange(LocationChange change) {
    	_changes.add(change);
    }
    
    private TracerUser getUserFromSender(CommandSender sender) {
    	Player player = (Player) sender;
    	
    	for(TracerUser user : this._users) {
    		if(user.getUUID().compareTo(player.getUniqueId()) == 0) {
    			return user;
    		}
    	}
    	
    	TracerUser newUser = new TracerUser(player.getUniqueId(), player.getLocation());
    	this._users.add(newUser);
    	return newUser;
    }
    
    public void onEnable() {
    	new ExplosionEvent(this);
    	new SandBlockEvent(this);
    	this._users = new ArrayList<TracerUser>();
    	_changes = new HashSet<LocationChange>();
    	entityLocations = new Hashtable<Integer, EntityLocation>();
    	this.tickCount = 0;
		this.vertices = new ArrayList<Offset>(Arrays.asList(
			new Offset(.49F, .49F, .49F),
			new Offset(-.49F, .49F, .49F),
			new Offset(-.49F, -.49F, .49F),
			new Offset(.49F, -.49F, .49F),
			new Offset(.49F, .49F, -.49F),
			new Offset(-.49F, .49F, -.49F),
			new Offset(-.49F, -.49F, -.49F),
			new Offset(.49F, -.49F, -.49F)));
    	
	    getCommand("toggletrace").setExecutor(this);
	    getCommand("cleartrace").setExecutor(this);
	    getCommand("mindistance").setExecutor(this);
	    getCommand("traceradius").setExecutor(this);
	    getCommand("maxparticles").setExecutor(this);
	    getCommand("tracetime").setExecutor(this);
	    getCommand("togglesandendpos").setExecutor(this);
	    getCommand("toggletntexplosion").setExecutor(this);
	    getCommand("toggletickconnect").setExecutor(this);
	    
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
	        public void run() {
	          main.this.tick();
	        }
        }, 0L, 1L);
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	TracerUser user = getUserFromSender(sender);
    	
    	if(cmd.getName().equalsIgnoreCase("toggletrace")) {
    		if(user.getTraceEnabled()) {
    			user.setTraceEnabled(false);
    			
    			user.clearParticleLocations();
    		}else {
    			user.setTraceEnabled(true);
    		}
    		
    		sender.sendMessage(ChatColor.GREEN + "Tracing is now: " + user.getTraceEnabled());
    	}else if(cmd.getName().equalsIgnoreCase("togglesandendpos")) {
    		user.setSandExplosionEnabled(!user.isSandExplosionEnabled());
    		
    		sender.sendMessage(ChatColor.GREEN + "Sand end position is now: " + user.isSandExplosionEnabled());
    	}else if(cmd.getName().equalsIgnoreCase("toggletntexplosion")) {
    		user.setTNTExplosionEnabled(!user.isTNTExplosionEnabled());
    		
    		sender.sendMessage(ChatColor.GREEN + "TNT explosion is now: " + user.isTNTExplosionEnabled());
    	}else if(cmd.getName().equalsIgnoreCase("toggletickconnect")) {
    		user.setTickConnect(!user.isTickConnect());
    		
    		sender.sendMessage(ChatColor.GREEN + "Tick connection is now: " + user.isTickConnect());
    	}else if(cmd.getName().equalsIgnoreCase("cleartrace")) {
    		user.clearParticleLocations();
    		
    		sender.sendMessage(ChatColor.GREEN + "Tracing cleared.");
    	}else if(cmd.getName().equalsIgnoreCase("mindistance") && args.length > 0) {
       	    int minDistance;
	        try {
	        	minDistance = Integer.parseInt(args[0]);
	        }catch(NumberFormatException e) {
	            sender.sendMessage(ChatColor.RED + "Not a valid number!");
	            return false;
	        }
	            
    		user.setMinTravelDistance(minDistance);
    		
    		sender.sendMessage(ChatColor.GREEN + "Min distance is now: " + user.getMinTravelDistance());
    	}else if(cmd.getName().equalsIgnoreCase("maxparticles") && args.length > 0) {
       	    int maxParticles;
	        try {
	        	maxParticles = Integer.parseInt(args[0]);
	        }catch(NumberFormatException e) {
	            sender.sendMessage(ChatColor.RED + "Not a valid number!");
	            return false;
	        }
	            
    		user.setMaxParticles(maxParticles);
    		
    		sender.sendMessage(ChatColor.GREEN + "Max particles is now: " + user.getMaxParticles());
    	}else if(cmd.getName().equalsIgnoreCase("traceradius") && args.length > 0) {
       	    int traceRadius;
	        try {
	        	traceRadius = Integer.parseInt(args[0]);
	        }catch(NumberFormatException e) {
	            sender.sendMessage(ChatColor.RED + "Not a valid number!");
	            return false;
	        }
	            
    		user.setMaxDistance(traceRadius);
    		
    		sender.sendMessage(ChatColor.GREEN + "Tracking radius is now: " + user.getMaxDistance());
    	}else if(cmd.getName().equalsIgnoreCase("tracetime") && args.length > 0) {
       	    int traceTime;
	        try {
	        	traceTime = Integer.parseInt(args[0]);
	        }catch(NumberFormatException e) {
	            sender.sendMessage(ChatColor.RED + "Not a valid number!");
	            return false;
	        }
	            
    		user.setTraceTime(traceTime);
    		
    		sender.sendMessage(ChatColor.GREEN + "Tracing will now last: " + user.getTraceTime() + " ticks.");
    	}

        return false;
    }
    
    private void processEntity(World world, Entity entity) {
        Location loc = entity.getLocation();
        int id = entity.getEntityId();
        
		if(entityLocations.containsKey(id)){
			Location old = entityLocations.get(id).getLocation();
			LocationChange change;

			if(entityLocations.get(id).getNew()) {
				change = new LocationChange(world, old, loc, entity.getType(), ChangeType.START);
			}else {
				change = new LocationChange(world, old, loc, entity.getType(), ChangeType.NORMAL);
			}
			
			if(change.getChangeType().compareTo(ChangeType.NORMAL) != 0 || old.distance(loc) != 0) {
			    _changes.add(change);
			}
			
			entityLocations.put(entity.getEntityId(), new EntityLocation(loc, false));
		}else {
			entityLocations.put(entity.getEntityId(), new EntityLocation(loc, true));
		}
    }
    
    public void tick() {    	
    	for(World world : getServer().getWorlds()) {
			for(Entity entity : world.getEntities()) {
	            if(entity.getType().equals(EntityType.FALLING_BLOCK) || entity.getType().equals(EntityType.PRIMED_TNT)) {
	                processEntity(world, entity);
	            }
			}
		}
    	
    	Set<Integer> keys = entityLocations.keySet();
    	ArrayList<Integer> removeList = new ArrayList<Integer>();
    	
    	for(int key : keys) {
    		if(entityLocations.get(key).getState()) {
    			entityLocations.get(key).kill();
    		}else {
    			removeList.add(key);
    		}
    	}
    	
    	for(int key : removeList) {
    		entityLocations.remove(key);
    	}
    	
    	for(TracerUser pu : this._users) {
    		if(pu.getTraceEnabled()) {
    			boolean continue_bool = false;
    			Collection<? extends Player> players = getServer().getOnlinePlayers();
    			
    			if(players != null) {
        			for(Player player : players) {
        				if(player.getUniqueId().compareTo(pu.getUUID()) == 0) {
        					continue_bool = true;
        					break;
        				}
        			}
    			}
    			if(continue_bool) {
    				Player player = getServer().getPlayer(pu.getUUID());
        			World world = player.getWorld();
        			
        			for(LocationChange change : _changes) {
        				if(change.getWorld() == world && (change.getStart().distance(change.getFinish()) >= pu.getMinTravelDistance() || change.getChangeType().compareTo(ChangeType.EXPLOTION) == 0)) {
        					if(player.getLocation().distance(change.getStart()) <= pu.getMaxDistance()) {
        						if(change.getChangeType().compareTo(ChangeType.EXPLOTION) == 0) {
        							if(change.getType().compareTo(EntityType.FALLING_BLOCK) == 0 && pu.isSandExplosionEnabled() || change.getType().compareTo(EntityType.PRIMED_TNT) == 0 && pu.isTNTExplosionEnabled()) {
            							int r, g, b;
            							
            							r = 0;
            							g = 0;
            							b = 255;
            							
            							for(Offset off : this.vertices) {
            								pu.addParticleLocation(new ParticleLocation(new Location(change.getWorld(), change.getFinish().getX() + off.getX(), change.getFinish().getY() + off.getY(), change.getFinish().getZ() + off.getZ()), pu.getTraceTime(), r, g, b));
            							}
        							}
        						}else {
            						int r, g, b;
            						
            						if(change.getType().compareTo(EntityType.PRIMED_TNT) == 0) {
            							r = 255;
            							g = 0;
            							b = 0;
            						}else {
            							r = 255;
            							g = 255;
            							b = 255;
            						}
            						
            						if(change.getType().compareTo(EntityType.FALLING_BLOCK) == 0 && CannoningUtil.in1x1(change.getStart())) {
            							pu.addParticleLocation(new ParticleLocation(change.getStart(), pu.getTraceTime(), 255, 255, 0));
            						}else {
            							pu.addParticleLocation(new ParticleLocation(change.getStart(), pu.getTraceTime(), r, g, b));
            						}
            						
            						if(pu.isTickConnect()) {
                						double offset = .25;
                						
                						if(change.getStart().getY() < change.getFinish().getY()) {
                    						while(change.getStart().getY() + offset < change.getFinish().getY()) {
                    							pu.addParticleLocation(new ParticleLocation(new Location(change.getWorld(), change.getStart().getX(), change.getStart().getY() + offset, change.getStart().getZ()), pu.getTraceTime(), r, g, b));
                    							offset += .25;
                    						}
                						}else {
                    						while(change.getStart().getY() - offset > change.getFinish().getY()) {
                    							pu.addParticleLocation(new ParticleLocation(new Location(change.getWorld(), change.getStart().getX(), change.getStart().getY() - offset, change.getStart().getZ()), pu.getTraceTime(), r, g, b));
                    							offset += .25;
                    						}
                						}
                						
                						pu.addParticleLocation(new ParticleLocation(new Location(change.getWorld(), change.getStart().getX(), change.getFinish().getY(), change.getStart().getZ()), pu.getTraceTime(), r, g, b));
                						
                						offset = .25;
                						
                						if(change.getStart().getX() < change.getFinish().getX()) {
                    						while(change.getStart().getX() + offset < change.getFinish().getX()) {
                    							pu.addParticleLocation(new ParticleLocation(new Location(change.getWorld(), change.getStart().getX() + offset, change.getFinish().getY(), change.getStart().getZ()), pu.getTraceTime(), r, g, b));
                    							offset += .25;
                    						}
                						}else {
                    						while(change.getStart().getX() - offset > change.getFinish().getX()) {
                    							pu.addParticleLocation(new ParticleLocation(new Location(change.getWorld(), change.getStart().getX() - offset, change.getFinish().getY(), change.getStart().getZ()), pu.getTraceTime(), r, g, b));
                    							offset += .25;
                    						}
                						}
                						
                						pu.addParticleLocation(new ParticleLocation(new Location(change.getWorld(), change.getFinish().getX(), change.getFinish().getY(), change.getStart().getZ()), pu.getTraceTime(), r, g, b));
                						
                						offset = .25;
                						
                						if(change.getStart().getZ() < change.getFinish().getZ()) {
                    						while(change.getStart().getZ() + offset < change.getFinish().getZ()) {
                    							pu.addParticleLocation(new ParticleLocation(new Location(change.getWorld(), change.getFinish().getX(), change.getFinish().getY(), change.getStart().getZ() + offset), pu.getTraceTime(), r, g, b));
                    							offset += .25;
                    						}
                						}else {
                    						while(change.getStart().getZ() - offset > change.getFinish().getZ()) {
                    							pu.addParticleLocation(new ParticleLocation(new Location(change.getWorld(), change.getFinish().getX(), change.getFinish().getY(), change.getStart().getZ() - offset), pu.getTraceTime(), r, g, b));
                    							offset += .25;
                    						}
                						}
            						}
            						
            						if(change.getType().compareTo(EntityType.FALLING_BLOCK) == 0 && CannoningUtil.in1x1(change.getFinish())) {
            							pu.addParticleLocation(new ParticleLocation(change.getFinish(), pu.getTraceTime(), 255, 255, 0));
            						}else {
            							pu.addParticleLocation(new ParticleLocation(change.getFinish(), pu.getTraceTime(), r, g, b));
            						}
        							
            						if(change.getChangeType().compareTo(ChangeType.START) == 0) {
            							r = 0;
            							g = 255;
            							b = 0;
            							
            							for(Offset off : this.vertices) {
            								pu.addParticleLocation(new ParticleLocation(new Location(change.getWorld(), change.getStart().getX() + off.getX(), change.getStart().getY() + off.getY(), change.getStart().getZ() + off.getZ()), pu.getTraceTime(), r, g, b));
            							}
            						}
        						}
        					}
        				}
        			}
        			
        			pu.updateRadius(player.getLocation());
        			PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        			Iterator<ParticleLocation> it = pu.getParticleLocations().iterator();

        			while(it.hasNext()) {
        				ParticleLocation plocation = it.next();
        				
        				if(plocation.getLocation().getWorld().equals(player.getWorld())) {
            				if(tickCount == 5 && (plocation.getLocation().distance(player.getLocation()) < pu.getViewRadius() || pu.isUnlimitedRadius())) {
        						PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.REDSTONE, true, (float)plocation.getLocation().getX(), (float)plocation.getLocation().getY() + 0.49F, (float)plocation.getLocation().getZ(), plocation.getR()/255, plocation.getG()/255, plocation.getB()/255, 1, 0);
        						connection.sendPacket(packet);
            				}
        				}
        				
        				plocation.decLifeSpan();
        				
        				if(plocation.getLifeSpan() == 0) {
        					it.remove();
        				}
        			}
    			}
    		}
    	}
    	
    	_changes.clear();
    	
    	if(this.tickCount == 5) {
    		this.tickCount = 0;
    	}else {
    		this.tickCount += 1;	
    	}
    }
}