package mobi.chouette.exchange.validation.report;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@XmlRootElement(name = "validation_report")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "result", "checkPoints" })
public class ValidationReport {

	@XmlElement(name = "result")
	@Getter
	@Setter
	private String result = "NO_VALIDATION";

	@XmlElement(name = "tests")
	@Getter
	@Setter
	private List<CheckPoint> checkPoints = new ArrayList<CheckPoint>();

	public CheckPoint findCheckPointByName(String name) {
		for (CheckPoint checkPoint : checkPoints) {
			if (checkPoint.getName().equals(name))
				return checkPoint;
		}
		return null;
	}

	public void checkResult() {
		result = checkPoints.isEmpty() ? "NO_VALIDATION" : "VALIDATION_PROCEDEED";
	}

	public JSONObject toJson() throws JSONException {
		JSONObject validationReport = new JSONObject();
		validationReport.put("result", result);
		if (!checkPoints.isEmpty()) {
			JSONArray tests = new JSONArray();
			for (CheckPoint checkPoint : checkPoints) {
				tests.put(checkPoint.toJson());
			}
			validationReport.put("tests", tests);
		}
		JSONObject object = new JSONObject();
		object.put("validation_report", validationReport);
		return object;
	}

}
