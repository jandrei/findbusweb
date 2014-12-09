package findbusweb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import findbusweb.agency.AgencyService;
import findbusweb.calendar.CalendarService;
import findbusweb.ed.Agency;
import findbusweb.ed.Calendars;
import findbusweb.ed.Historico;
import findbusweb.ed.Routes;
import findbusweb.ed.Shapes;
import findbusweb.ed.StopTime;
import findbusweb.ed.Stops;
import findbusweb.ed.Trips;
import findbusweb.historico.HistoricoService;
import findbusweb.routes.RouteService;
import findbusweb.shapes.ShapeService;
import findbusweb.stops.StopService;
import findbusweb.stoptime.StopTimeService;
import findbusweb.trips.TripService;
import findbusweb.util.GeoCoordinate;

@Path("/r")
@ManagedBean
public class Rest {
	
	Logger logger = LoggerFactory.getLogger(Rest.class);

	@Inject
	AgencyService agencyService;

	@Inject
	RouteService routeService;

	@Inject
	StopService stopService;

	@Inject
	CalendarService calendarService;

	@Inject
	TripService tripService;

	@Inject
	ShapeService shapeService;

	@Inject
	StopTimeService stopTimeService;

	@Inject
	HistoricoService historicoService;

	@Context
	Request request;

	@GET
	@Path("/agencys")
	public Response agencys() {
		java.util.List<Agency> agencias = agencyService.listar(new Agency());
		return Response.ok(new Gson().toJson(agencias)).build();
	}

	@GET
	@Path("/routes")
	public Response routes() {
		java.util.List<Routes> rotas = routeService.listar(new Routes());
		return Response.ok(new Gson().toJson(rotas)).build();
	}

	@GET
	@Path("/stops")
	public Response stops() {
		java.util.List<Stops> rotas = stopService.listar(new Stops());
		return Response.ok(new Gson().toJson(rotas)).build();
	}

	@GET
	@Path("/calendars")
	public Response calendars() {
		java.util.List<Calendars> lista = calendarService.listar(new Calendars());
		return Response.ok(new Gson().toJson(lista)).build();
	}

	@GET
	@Path("/trips")
	public Response trips(@Context HttpServletRequest request) {
		String page = request.getParameter("page");
		String max = request.getParameter("max");

		if (page == null || page.isEmpty() || max == null || max.isEmpty()) {
			return Response.ok("max e page devem ser informados").build();
		}

		java.util.List<Trips> lista = tripService.listarTodos(Integer.valueOf(max), Integer.valueOf(page));
		return Response.ok(new Gson().toJson(lista)).build();
	}

	@GET
	@Path("/shapes")
	public Response shapes(@Context HttpServletRequest request) {
		String page = request.getParameter("page");
		String max = request.getParameter("max");
		if (page == null || page.isEmpty() || max == null || max.isEmpty()) {
			return Response.ok("max e page devem ser informados").build();
		}

		java.util.List<Shapes> lista = shapeService.listarTodos(Integer.valueOf(max), Integer.valueOf(page));
		return Response.ok(new Gson().toJson(lista)).build();
	}

	@GET
	@Path("/stoptime")
	public Response stopTime(@Context HttpServletRequest request) {
		String page = request.getParameter("page");
		String max = request.getParameter("max");
		if (page == null || page.isEmpty() || max == null || max.isEmpty()) {
			return Response.ok("max e page devem ser informados").build();
		}

		java.util.List<StopTime> lista = stopTimeService.listarTodos(Integer.valueOf(max), Integer.valueOf(page));
		return Response.ok(new Gson().toJson(lista)).build();
	}

	@POST
	@Path("/adicionaHistorico")
	public Response adicionaHistorico(String json) {
		Historico historico = new GsonBuilder().setDateFormat("dd/MMM/yyyy HH:mm:ss").create().fromJson(json, Historico.class);

		System.out.println(json);

		historicoService.salvar(historico);

		return Response.ok().build();
	}

	@GET
	@Path("/pegaUltimoHistoricoPorStop")
	public Response pegaUltimoHistoricoPorStop(@Context HttpServletRequest request) {
		String routeId = request.getParameter("routeId");

		if (routeId == null || routeId.trim().isEmpty()) {
			return Response.ok().build();
		}
		return Response.ok(new Gson().toJson(historicoService.pegaUltimoHistoricoPorStop(routeId))).build();
	}
	
	@GET
	@Path("/pegaHistoricoRoutes")
	public Response pegaHistoricoRoutes(@Context HttpServletRequest request) {
		String routeId = request.getParameter("routeId");

		if (routeId == null || routeId.trim().isEmpty()) {
			return Response.ok().build();
		}
		
		List<Historico> lista = historicoService.pegaHistoricoRoutes(routeId);
		
		lista = listaHistoricosAgrupados(lista);
		
		return Response.ok(new Gson().toJson(lista)).build();
	}

	private List<Historico> listaHistoricosAgrupados(List<Historico> lista){
		if (lista == null || lista.isEmpty()){
			return new ArrayList<Historico>();
		}

		System.out.println("------------------------------------");
		List<Historico> resultado = new ArrayList<Historico>();
		Collections.shuffle(lista);
		
		Historico h = null;
		
		for (int i = 0; i < lista.size(); i++) {
			if (h == null){
				h = lista.get(i);
				resultado.add(h);
				continue;
			}
			Historico h2 = lista.get(i);
			
			GeoCoordinate inicio = new GeoCoordinate(h.getLatitude().doubleValue(), h.getLongitude().doubleValue());
			GeoCoordinate fim = new GeoCoordinate(h2.getLatitude().doubleValue(), h2.getLongitude().doubleValue());
			
			
			if (inicio.distanceInKm(fim) > 0.25){
				int statusInicio = Integer.valueOf(h.getIndStatus());
				int statusFim = Integer.valueOf(h.getIndStatus());
				resultado.add(statusInicio >statusFim ? h :h2);
				
				System.out.println("S "+inicio.distanceInKm(fim) + " - " + (inicio.distanceInKm(fim) / 1000));
			}else{
				System.out.println("N "+ inicio.distanceInKm(fim) + " - " + (inicio.distanceInKm(fim) / 1000));
				
			}
			
			h = h2;
		}
		
		if (lista.size() != resultado.size()){
			return listaHistoricosAgrupados(resultado);
		}
		
		return resultado;
	}
	
}
