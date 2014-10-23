package fr.certu.chouette.exchange.gtfs.refactor.importer;

import java.awt.Color;
import java.io.IOException;

import fr.certu.chouette.exchange.gtfs.refactor.model.GtfsRoute;

public class RouteById extends IndexImpl<GtfsRoute> implements GtfsConverter
{

   public static enum FIELDS
   {
      route_id, agency_id, route_short_name, route_long_name, route_desc, route_type, route_url, route_color, route_text_color;
   };

   public static final String FILENAME = "routes.txt";
   public static final String KEY = FIELDS.route_id.name();

   private GtfsRoute bean = new GtfsRoute();
   private String[] array = new String[FIELDS.values().length];
   
   public RouteById(String name) throws IOException
   {
      super(name, KEY);
   }

   @Override
   protected GtfsRoute build(GtfsIterator reader, int id)
   {
      int i = 0;
      for (FIELDS field : FIELDS.values())
      {
         array[i++] = getField(reader, field.name());        
      }
      
      i = 0;
      bean.setId(id);      
      bean.setRouteId(STRING_CONVERTER.from(array[i++], true));
      bean.setAgencyId(STRING_CONVERTER.from(array[i++], false));
      bean.setRouteShortName(STRING_CONVERTER.from(array[i++], true));
      bean.setRouteLongName(STRING_CONVERTER.from(array[i++], true));
      bean.setRouteDesc(STRING_CONVERTER.from(array[i++], false));
      bean.setRouteType(ROUTETYPE_CONVERTER.from(array[i++], true));
      bean.setRouteUrl(URL_CONVERTER.from(array[i++], false));
      bean.setRouteColor(COLOR_CONVERTER.from(array[i++], Color.WHITE, false));
      bean.setRouteTextColor(COLOR_CONVERTER.from(array[i++],Color.BLACK, false));
     
      return bean;
   }

   @Override
   public boolean validate(GtfsRoute bean, GtfsImporter dao)
   {
      return true;
   }

   public static class DefaultImporterFactory extends IndexFactory
   {
      @Override
      protected Index create(String name) throws IOException
      {
         return new RouteById(name);
      }
   }

   static
   {
      IndexFactory factory = new DefaultImporterFactory();
      IndexFactory.factories.put(RouteById.class.getName(), factory);
   }

}
