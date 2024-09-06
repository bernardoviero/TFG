+!handle_cfp <- 
    .my_name(Name);
    ?fuel(Fuel);
    ?altitude(Altitude);
    .send(controlador, propose, [Fuel, Altitude]).

+configured(fuel, Fuel) <- 
    +fuel(Fuel).

+configured(altitude, Altitude) <- 
    +altitude(Altitude).

+!set_fuel(Fuel) <- 
    .println("Setting fuel level: ", Fuel);
    focus(env, agent_data); 
    cartago.doAction("agent_data", "setFuel", [Fuel]).

+!set_altitude(Altitude) <- 
    .println("Setting altitude: ", Altitude);
    focus(env, agent_data);
    cartago.doAction("agent_data", "setAltitude", [Altitude]).
