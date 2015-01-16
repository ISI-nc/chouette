/**
 * 
 */
package fr.certu.chouette.export.metadata.writer;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import fr.certu.chouette.export.metadata.model.Metadata.Box;
import fr.certu.chouette.export.metadata.model.Metadata.Period;
import fr.certu.chouette.export.metadata.model.Metadata.Resource;

/**
 * @author michel
 *
 */
public class TextFormater implements Formater
{

   private static DecimalFormat doubleFormat = new DecimalFormat("#.000"); 
   private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
   /* (non-Javadoc)
    * @see fr.certu.chouette.export.metadata.writer.Formater#format(fr.certu.chouette.export.metadata.model.Metadata.Period)
    */
   @Override
   public String format(Period period)
   {
      return "du "+dateFormat.format(period.getStart().getTime())+
            " au "+dateFormat.format(period.getEnd().getTime());
   }

   /* (non-Javadoc)
    * @see fr.certu.chouette.export.metadata.writer.Formater#format(fr.certu.chouette.export.metadata.model.Metadata.Box)
    */
   @Override
   public String format(Box box)
   {
      return " nord "+doubleFormat.format(box.getNorthLimit())+
            ", sud "+doubleFormat.format(box.getSouthLimit())+
            ", ouest "+doubleFormat.format(box.getWestLimit())+
            ", est "+doubleFormat.format(box.getEastLimit());
   }

   /* (non-Javadoc)
    * @see fr.certu.chouette.export.metadata.writer.Formater#format(java.util.List)
    */
   @Override
   public String format(Resource resource)
   {
      StringBuilder builder = new StringBuilder();
      if (resource.getFileName() != null)
      {
         builder.append(resource.getFileName()+" : ");
      }
      if (resource.getNetworkName() != null)
      {
         builder.append("réseau "+resource.getNetworkName()+", ");
      }
      builder.append("ligne "+resource.getLineName());

      return builder.toString();
   }

   /* (non-Javadoc)
    * @see fr.certu.chouette.export.metadata.writer.Formater#formatDate(java.util.Calendar)
    */
   @Override
   public String formatDate(Calendar date)
   {
      return dateFormat.format(date.getTime());
   }


}
