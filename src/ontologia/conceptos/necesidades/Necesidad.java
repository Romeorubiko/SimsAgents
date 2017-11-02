package ontologia.conceptos.necesidades;

import jadex.runtime.*;
import ontologia.Concepto; import ontologia.conceptos.habilidades.*; import ontologia.conceptos.necesidades.*; import ontologia.conceptos.*;

public abstract class Necesidad extends Concepto {
	public static final int NC_POCO = 1;
	public static final int NC_NORMAL = 3;
	public static final int NC_MUCHO = 5;
	public Necesidad(){;}
	
}