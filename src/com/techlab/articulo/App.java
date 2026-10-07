package com.techlab.articulo;

import java.util.ArrayList;
import java.util.Scanner;

import com.techlab.articulo.model.*;

public class App {
    public static void main(String[] args) throws Exception {
       

        Scanner scanner = new Scanner(System.in);

        ArrayList<Articulo> articulos = new ArrayList<>();
        ArrayList<Categoria> categorias = new ArrayList<>();
        precargarCategorias(categorias);

        int opcion; 

        do {
            System.out.println("\n====================================");
            System.out.println("Bienvenido al Sistema");
            System.out.println("====================================");
            System.out.println("1 - Ingresar Articulo");
            System.out.println("2 - Listar Articulos");
            System.out.println("3 - Consultar un Articulo");
            System.out.println("4 - Modificar un Articulo");
            System.out.println("5 - Eliminar un Articulo");
            System.out.println("6 - Listar categorias");
            System.out.println("0 - Salir del Sistema");
            System.out.println("====================================");

            opcion = leerEntero(scanner, "Ingrese una opcion 1 - 5 o 0 para salir: ");

            switch (opcion){
                case 1:
                    ingresarArticulo(scanner, articulos, categorias);
                    break;
                case 2:
                    listarArticulo(articulos);
                    break;
                case 3:
                    consultarArticulo(scanner, articulos);
                    break;
                case 4:
                    modificarArticulo(scanner, articulos, categorias);
                    break;
                case 5:
                    eliminarArticulo(scanner, articulos);
                    break;
                case 6:
                    listarCategorias(categorias);
                case 0 :
                    System.out.println("\nSaliendo del sistema. Hasta Luego! ");
                    break;
                default:
                    System.out.println("\nError: la opcion ingresada es invalida");
            }

        } while (opcion != 0);

        scanner.close();

    }

    
        public static void ingresarArticulo(Scanner sc, ArrayList<Articulo> articulos, ArrayList<Categoria> categorias){

            System.out.println("\n---  INGRESAR ARTICULO ---");
            System.out.println("1 - Articulo electronico");
            System.out.println("2 - Articulo Alimenticio");

            int tipo;
            do{
                tipo = leerEntero(sc, "Seleccion el tipo de articulo: ");

                if (tipo != 1 && tipo != 2){
                    System.out.println("Error: debe  elegir 1 o 2");
                }
            } while (tipo != 1 && tipo!= 2);
            
            int codigo = leerEntero(sc, "Ingrese el codigo del articulo: ");

            if(buscarArticuloPorCodigo(articulos, codigo) != null){
                System.out.println("Error: ya existe un articulo con ese codigo.");
                return;
            }

            String nombre = leerTextoNoVacio(sc, "Ingrese el nombre del articulo: ");
            double precio = leerDoubleNoNegativo(sc, "Ingrese el precio del articulo");

            listarCategorias(categorias);
            Categoria categoria = pedirCategoriaExistente(sc, categorias);

            Articulo articulo;

            if (tipo == 1){
                int garantiaMeses = leerEnteroNoNegativo(sc, "Ingrese la garantia en meses: ");
                articulo = new ArticuloElectronico(codigo, nombre, precio, categoria, garantiaMeses);
            } else {
                int diasParaVencimiento = leerEnteroNoNegativo(sc, "Ingrese los dias para vencimiento: ");
                articulo = new ArticuloAlimenticio(codigo, nombre, precio, categoria, diasParaVencimiento);
            }

            articulos.add(articulo);
            System.out.println("Resumen del articulo creado: ");
            System.out.println(articulo);
        }

        public static void listarCategorias(ArrayList<Categoria> categorias){
            System.out.println("\n--- CATEGORIAS DISPONIBLES---");

            for(Categoria categoria: categorias){
                System.out.println(categoria);
            }
        }

        public static Categoria pedirCategoriaExistente(Scanner sc, ArrayList<Categoria> categorias){
            while(true){
                int codigoCategoria = leerEntero(sc, "Ingrese el codigo de la categoria");

                Categoria categoria = buscarCategoriaPorCodigo(categorias, codigoCategoria);

                if (categoria != null){
                    return categoria;
                }

                System.out.println("Error: la categoria no existe.");
            }
        }

        public static Categoria buscarCategoriaPorCodigo(ArrayList<Categoria> categorias, int codigoCategoria){
            for(Categoria categoria: categorias){
                if(categoria.getCodigo() == codigoCategoria){
                    return categoria;
                }
            }
            return null;
        }

        public static Articulo buscarArticuloPorCodigo(ArrayList<Articulo> articulos, int codigo){
            for(Articulo articulo: articulos){
                if(articulo.getCodigo() == codigo){
                    return articulo;
                }
            }
            return null;
        }

        public static int leerEntero(Scanner sc, String mensaje){

            while(true){

                try {
                    System.out.println(mensaje);
                    return Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e){
                    System.out.println("Error: debe ingresar un numero valido. ");
                }
                
            }
        }

        public static int leerEnteroNoNegativo(Scanner sc, String mensaje){
            while(true){
                int valor = leerEntero(sc, mensaje);
                if(valor < 0){
                    System.out.println("Error: el valor no puede ser negativo.");
                    continue;
                }
                return valor;
            }
        }

        public static double leerDoubleNoNegativo(Scanner sc, String mensaje){
            while(true){
                try {
                    System.out.println(mensaje);
                    double valor = Double.parseDouble(sc.nextLine());

                    if(valor <0){
                        System.out.println("Error: el precio no puede ser negativo");
                        continue;
                    }
                    return valor;
                } catch (NumberFormatException e) {
                    System.out.println("Error: debe ingresar un numero decimal valido.");
                }
            }
        }

        public static String leerTextoNoVacio(Scanner sc, String mensaje){

            while(true){
                System.out.println(mensaje);
                String texto = sc.nextLine();

                if(!texto.trim().isEmpty()){
                    return texto.trim();
                }

                System.out.println("Error: el texto no puede estar vacio. ");
            }
        }

        public static boolean existeArticulo(String articuloABuscar, ArrayList<String> articulos){

            return articulos.stream()
                                    .anyMatch(articulo -> articulo.equalsIgnoreCase(articuloABuscar.trim()));
        }

        public static void listarArticulo(ArrayList<Articulo> articulos){

            System.out.println("\n--- LISTADO DE ARTICULOS ---");

            if(articulos.isEmpty()){
                System.out.println("No hay articulos cargados. ");
                return;
            }
            
            for (Articulo articulo: articulos){
                System.out.println(articulo);
                if(articulo instanceof ArticuloElectronico electronico){
                    electronico.nroTelMesaDeAyudaParaReclamos();
                }
            }
        }

        public static void consultarArticulo(Scanner sc, ArrayList<Articulo> articulos){
            System.out.println("\n--- CONSULTAR ARTICULO --- ");

            if (articulos.isEmpty()){
                System.out.println("Error: No hay articulos cargados. ");
                return;
            }

            int codigo = leerEntero(sc, "Ingrese el codigo del articulo  a consultar");

            Articulo articulo = buscarArticuloPorCodigo(articulos, codigo);

            if(articulo == null){
                System.out.println("Error: El articulo no existe.");
                return;
            }

            System.out.println("Articulo encontrado: ");
            System.out.println(articulo);
        }

        public static void modificarArticulo(Scanner sc, ArrayList<Articulo> articulos, ArrayList<Categoria> categorias){

            System.out.println("\n--- MODIFICAR ARTICULO ---");

            if (articulos.isEmpty()){
                System.out.println("No hay articulos cargados. ");
                return;
            }

            int codigo = leerEntero(sc, "Ingrese el codigo del articulo que desea modificar");
            
            Articulo articulo = buscarArticuloPorCodigo(articulos, codigo);

            if (articulo == null){
                System.out.println("Error: El articulo no existe");
                return;
            }

            String nuevoNombre = leerTextoNoVacio(sc, "Ingrese el nuevo nombre del articulo");
            double nuevoPrecio = leerDoubleNoNegativo(sc, "Ingrese el nuevo precio del articulo");

            listarCategorias(categorias);
            Categoria nuevaCategoria = pedirCategoriaExistente(sc, categorias);

            articulo.setNombre(nuevoNombre);
            articulo.setPrecio(nuevoPrecio);
            articulo.setCategoria(nuevaCategoria);
            
            if (articulo instanceof ArticuloElectronico electronico){
                int nuevaGarantia = leerEnteroNoNegativo(sc, "Ingrese la nueva garantia en meses");
                electronico.setGarantiaMeses(nuevaGarantia);
            }

            if (articulo instanceof ArticuloAlimenticio alimenticio) {
                int nuevoVencimiento = leerEnteroNoNegativo(sc, "Ingrese el nuevo vencimiento en dias");
                alimenticio.setDiasParaVencimiento(nuevoVencimiento);
            }

            System.out.println("Articulo modificado correctamente");
        }

        public static int buscarPosicionArticulo(ArrayList<String> articulos, String articulo){
            for(int i = 0; i < articulos.size(); i ++){
                if(articulos.get(i).equalsIgnoreCase(articulo.trim())){
                    return i;
                }
            }
            return -1;
        }

        public static void eliminarArticulo(Scanner sc, ArrayList<Articulo> articulos){

            System.out.println("\n--- ELIMINAR ARTICULO ---");

            if (articulos.isEmpty()){
                System.out.println("No hay articulos cargados.");
                return;
            }

            int codigo = leerEntero(sc, "Ingrese el codigo del articulo que decia eliminar");

            Articulo articulo = buscarArticuloPorCodigo(articulos, codigo);

            if (articulo == null){
                System.out.println("Error: El articulo no existe");
                return;
            }

            articulos.remove(articulo);
            System.out.println("Articulo eliminado con existo!");

        }

        public static void precargarCategorias(ArrayList<Categoria> categorias){
            categorias.add(new Categoria(1, "Electronica", "Productos tecnologicos y electronicos"));
            categorias.add(new Categoria(2, "Perisfericos", "Accesorios para computadora"));
            categorias.add(new Categoria(3, "Alimentos", "Productos alimenticios"));
            categorias.add(new Categoria(4, "Limpieza", "Articulos de limpieza para el hogar"));

        }
}
