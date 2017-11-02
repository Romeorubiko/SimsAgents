# SimsAgents
Código jadex para los agentes que conllevará la practica de los Sims (segunda parte)


## Configuración JADEX

Para los que tengan macOS esta pregunta en Stackoverflow les puede ayudar a configurar el CLASSPATH y que JADEX fucione correctamente.

[https://stackoverflow.com/a/1676261/2931833](https://stackoverflow.com/a/1676261/2931833)

Solo hay que copiar los .jar que hay en la carpeta ```jadex-0.96/lib``` dentro de ```/Library/Java/Extensions```

## Valores globales

Para los tres tiempos de refresco disponibles hemos creado las variables estáticas TIEMPO_CORTO, TIEMPO_MEDIO, TIEMPO_LARGO en la clase Accion (a la que debeis referenciar)
Para los tres grados en los que aumenta/disminuye las necesidades/habilidades(experiencia) hemos creado tres variables en las clases Necesidad y Habilidad respectivamente. 
