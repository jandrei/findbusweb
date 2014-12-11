package br.com.senac.findbus.view;

import br.com.senac.findbus.model.CustomED;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MarkerUtil<T extends CustomED> {

	public MarkerUtil(T ed, Marker marker, MarkerOptions markerOptions) {
		super();
		this.ed = ed;
		this.marker = marker;
		this.markerOptions = markerOptions;
	}

	T ed;
	Marker marker;
	MarkerOptions markerOptions;

	public T getEd() {
		return ed;
	}

	public Marker getMarker() {
		return marker;
	}

	public MarkerOptions getMarkerOptions() {
		return markerOptions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((marker == null) ? 0 : marker.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MarkerUtil other = (MarkerUtil) obj;
		if (marker == null) {
			if (other.marker != null)
				return false;
		} else if (!marker.equals(other.marker))
			return false;
		return true;
	}

}
