package ca.mcgill.ecse223.kingdomino.server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonToMethodMapper {
	enum Status {
		ERROR,
		EXIT,
		OK
	}

	private String exitCmd;
	Map<String, List<Method>> routes = new HashMap<>();
	
	public JsonToMethodMapper(String exitCmd) {
		this.exitCmd = exitCmd;
	}
	
	public JsonToMethodMapper(JsonToMethodMapper mapper, String exitCmd) {
		this(exitCmd);
		this.routes.putAll(mapper.routes);
	}
	
	public synchronized boolean put(String cmd, Method method) {
		if (!routes.containsKey(cmd)) {
			routes.put(cmd, new ArrayList<>());
		}
		return routes.get(cmd).add(method);
	}

	public Method getMethod(String name, int numberOfParams) {
		// find the matching method
		final List<Method> possibleMethods = routes.get(name);

		if (possibleMethods != null) {
			for (Method m : possibleMethods) {
				if (m.getParameterCount() == numberOfParams) {
					return m;
				}
			}
		}

		return null;
	}
	
	public Map<String, Object> process(Map<String, Object> json) throws IllegalArgumentException {
		final String cmd = (String) json.get("cmd");
		
		final List<Object> args = new ArrayList<>();
		((Iterable<Object>)json.get("args")).forEach(args::add);

		Map<String, Object> ret = new HashMap<>();

		ret.put("cmd", cmd);

		Method method = getMethod(cmd, args.size());
		if (method == null) {
			ret.put("status", Status.ERROR.toString().toLowerCase());
			ret.put("data", "the cmd `" + cmd + "` is undefined");
			return ret;
		}

		// fill up string arguments as Object
		final Object[] argsAsObject = new Object[method.getParameterCount()];
		for (int i = 0; i < argsAsObject.length; ++i) {
			argsAsObject[i] = args.get(i);
		}

		// the method invoking phase
		try {
			final Object result = (Object) method.invoke(null, argsAsObject);
			if (exitCmd.equalsIgnoreCase(cmd)) {
				ret.put("status", Status.EXIT.toString().toLowerCase());
			} else {
				ret.put("status", Status.OK.toString().toLowerCase());
			}
			ret.put("data", result);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			ret.put("status", Status.ERROR.toString().toLowerCase());
			ret.put("data", e.getMessage());
		}
		
		return ret;
	}
	
	public synchronized String getExitCmd() {
		return exitCmd;
	}

	public synchronized void setExitCmd(String exitCmd) {
		this.exitCmd = exitCmd;
	}
}
