package fr.certu.chouette.model.neptune;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

import lombok.Getter;
import lombok.Setter;
import fr.certu.chouette.model.neptune.type.BoardingAlightingPossibilityEnum;

/**
 * model for VehicleJourneyAtStop Neptune Object
 * 
 * @author michel
 *
 */
public class VehicleJourneyAtStop extends NeptuneObject 
{
	private static final long serialVersionUID = 194243517715939830L;
	/**
	 * Trident Id of associated StopPoint 
	 */
	@Getter @Setter private String stopPointId;
	/**
	 * associated StopPoint
	 */
	@Getter @Setter private StopPoint stopPoint;
	/**
	 * Trident Id of associated VehicleJourney 
	 */
	@Getter @Setter private String vehicleJourneyId;
	/**
	 * associated VehicleJourney
	 */
	@Getter @Setter private VehicleJourney vehicleJourney;
	/**
	 * 
	 */
	@Getter @Setter private String connectingServiceId;
	/**
	 * 
	 */
	@Getter @Setter private BoardingAlightingPossibilityEnum boardingAlightingPossibility;
	/**
	 * order in journeyPattern
	 */
	@Getter @Setter private long order;
	/**
	 * arrival time
	 */
	@Getter @Setter private Time arrivalTime;
	/**
	 * departure time
	 */
	@Getter @Setter private Time departureTime;
	/**
	 * waiting time
	 */
	@Getter @Setter private Time waitingTime;
	@Getter @Setter private Time elapseDuration;
	@Getter @Setter private Time headwayFrequency;
	@Getter @Setter private boolean departure;
	//	/* (non-Javadoc)
	//	 * @see fr.certu.chouette.model.neptune.NeptuneBean#expand(fr.certu.chouette.manager.NeptuneBeanManager.DETAIL_LEVEL)
	//	 */
	//	@Override
	//	public void expand(DetailLevelEnum level)
	//	{
	//		// to avoid circular call check if level is already set according to this level
	//		if (getLevel().ordinal() >= level.ordinal()) return;
	//		super.expand(level);
	//		switch (level)
	//		{
	//		case ATTRIBUTE : 
	//			stopPoint = null;
	//			vehicleJourney = null;
	//			break;
	//		case NARROW_DEPENDENCIES : 
	//		case STRUCTURAL_DEPENDENCIES : 
	//		case ALL_DEPENDENCIES :
	//			if (getStopPoint() != null) getStopPoint().expand(DetailLevelEnum.ATTRIBUTE);
	//			if (getVehicleJourney() != null) getVehicleJourney().expand(DetailLevelEnum.ATTRIBUTE);
	//			break;
	//		}
	//	} 

	/* (non-Javadoc)
	 * @see fr.certu.chouette.model.neptune.NeptuneObject#toString(java.lang.String, int)
	 */
	@Override
	public String toString(String indent, int level) {
		StringBuilder sb = new StringBuilder(super.toString(indent,level));
		sb.append("\n").append(indent).append("stopPointId = ").append(stopPointId);
		sb.append("\n").append(indent).append("vehicleJourneyId = ").append(vehicleJourneyId);
		sb.append("\n").append(indent).append("connectingServiceId = ").append(connectingServiceId);
		sb.append("\n").append(indent).append("boardingAlightingPossibility = ").append(boardingAlightingPossibility);
		sb.append("\n").append(indent).append("order = ").append(order);
		sb.append("\n").append(indent).append("arrivalTime = ").append(formatDate(arrivalTime));
		sb.append("\n").append(indent).append("departureTime = ").append(formatDate(departureTime));
		sb.append("\n").append(indent).append("waitingTime = ").append(formatDate(waitingTime));
		sb.append("\n").append(indent).append("elapseDuration = ").append(formatDate(elapseDuration));
		sb.append("\n").append(indent).append("headwayFrequency = ").append(formatDate(headwayFrequency));
		if (level >= 1)
		{
			if (stopPoint != null)
			{
				sb.append("\n").append(indent).append("stopPoint.id = ").append(stopPoint.getId());
				sb.append("\n").append(indent).append("stopPoint.objectId = ").append(stopPoint.getObjectId());
				if (stopPoint.getContainedInStopArea() != null)
					sb.append("\n").append(indent).append("stopPoint.name = ").append(stopPoint.getContainedInStopArea().getName());
			}
		}

		return sb.toString();
	}

	/**
	 * convert time to string for toString purpose 
	 * @param date 
	 * @return
	 */
	private String formatDate(Date date)
	{
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		if(date != null)
		{
			return dateFormat.format(date);
		}
		else
		{
			return null;
		}
	}

	/**
	 * check and return stoppoint if objectId match
	 * @param objectId stopPoint objectid to check
	 * @return stoppoint
	 */
	public  StopPoint getStopPointByObjectId(String objectId)
	{
		if(stopPoint != null)
		{
			if(stopPoint.getObjectId().equals(objectId))
				return stopPoint;
			else
				return null;	
		}
		else
		{
			return null;
		}

	}


	@Override
	public boolean equals(Object obj) 
	{
		if (obj instanceof VehicleJourneyAtStop)
		{
			VehicleJourneyAtStop vobj = (VehicleJourneyAtStop) obj;
			if (getId() == null && vobj.getId() == null)
			{
				if (getStopPoint() != null )
				{
					return getStopPoint().equals(vobj.getStopPoint());
				}
			}
		}
		return super.equals(obj);
	}

}
