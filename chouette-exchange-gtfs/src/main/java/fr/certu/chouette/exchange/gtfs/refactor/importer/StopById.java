package fr.certu.chouette.exchange.gtfs.refactor.importer;

import java.io.IOException;
import java.math.BigDecimal;

import fr.certu.chouette.exchange.gtfs.refactor.model.GtfsStop;
import fr.certu.chouette.exchange.gtfs.refactor.model.GtfsStop.LocationType;
import fr.certu.chouette.exchange.gtfs.refactor.model.GtfsStop.WheelchairBoardingType;

public class StopById extends IndexImpl<GtfsStop> implements GtfsConverter
{

   public static enum FIELDS
   {
      stop_id, stop_code, stop_name, stop_desc, stop_lat, stop_lon, zone_id, stop_url, location_type, parent_station, stop_timezone, wheelchair_boarding;
   };

   public static final String FILENAME = "stops.txt";
   public static final String KEY = FIELDS.stop_id.name();

   private GtfsStop bean = new GtfsStop();
   private String[] array = new String[FIELDS.values().length];
   private String _stopId = null;

   public StopById(String name) throws IOException
   {
      super(name, KEY);
   }

   @Override
   protected GtfsStop build(GtfsIterator reader, int id)
   {
      int i = 0;
      for (FIELDS field : FIELDS.values())
      {
         array[i++] = getField(reader, field.name());
      }

      i = 0;
      bean.setId(id);

      bean.setStopId(STRING_CONVERTER.from(array[i++], true));
      bean.setStopCode(STRING_CONVERTER.from(array[i++], false));
      bean.setStopName(STRING_CONVERTER.from(array[i++], true));
      bean.setStopDesc(STRING_CONVERTER.from(array[i++], false));
      bean.setStopLat(BigDecimal.valueOf(FLOAT_CONVERTER.from(array[i++], true)));
      bean.setStopLon(BigDecimal.valueOf(FLOAT_CONVERTER.from(array[i++], true)));
      bean.setZoneId(STRING_CONVERTER.from(array[i++], false));
      bean.setStopUrl(URL_CONVERTER.from(array[i++], false));
      bean.setLocationType(LOCATIONTYPE_CONVERTER.from(array[i++],
            LocationType.Stop, false));
      bean.setParentStation(STRING_CONVERTER.from(array[i++], false));
      bean.setStopTimezone(TIMEZONE_CONVERTER.from(array[i++], false));
      bean.setWheelchairBoarding(WHEELCHAIRBOARDINGTYPE_CONVERTER.from(
            array[i++], WheelchairBoardingType.NoInformation, false));

      return bean;
   }

   @Override
   public boolean validate(GtfsStop bean, GtfsImporter dao)
   {
      boolean result = true;
      String stopId = bean.getParentStation();
      if (stopId != null && !stopId.equals(_stopId))
      {
         if (stopId != null)
         {
            if (!containsKey(stopId))
            {
               throw new GtfsException("[DSU] error stop_id : " + stopId);
            }
            GtfsStop parent = getValue(stopId);
            if (parent == null
                  || parent.getLocationType() != LocationType.Station)
            {
               throw new GtfsException("[DSU] error stop_id : " + stopId);
            }
            _stopId = stopId;
         }
      }

      return result;
   }

   public static class DefaultImporterFactory extends IndexFactory
   {
      @Override
      protected Index create(String name) throws IOException
      {
         return new StopById(name);
      }
   }

   static
   {
      IndexFactory factory = new DefaultImporterFactory();
      IndexFactory.factories.put(StopById.class.getName(), factory);
   }
}
