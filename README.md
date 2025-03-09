# Ecosystem Simulation

A Java-based ecosystem simulation that models the interactions between plants and animals in a 2D environment.

## Project Overview

This simulation creates a virtual ecosystem where plants and animals coexist and interact on a 2D grid. The simulation runs for a specified duration or until the ecosystem becomes unbalanced.

### Key Features

- 2D grid-based environment
- Plant and animal populations with distinct behaviors
- Energy-based interaction system
- Reproduction mechanics
- Aging and death mechanics
- Configurable simulation parameters
- Real-time visualization
- CSV output for data analysis

## Project Structure

```
isla-entrega/
├── src/main/java/isla/
│   ├── Ajustes.java       # Configuration and constants
│   ├── Animal.java        # Animal behavior and movement
│   ├── Celda.java        # Grid cell implementation
│   ├── Creacion.java     # Entity creation and initialization
│   ├── GestorPoblacion.java # Population management
│   ├── Main.java         # Main simulation loop
│   ├── Planta.java       # Plant behavior
│   ├── Random.java       # Random number utilities
│   ├── Salida.java       # Output handling
│   ├── Ser.java         # Base class for living entities
│   ├── Tablero.java     # Grid management
│   └── Vida.java        # Life cycle management
└── target/classes/       # Compiled classes
```

## Configuration

All simulation parameters are centralized in the `Ajustes` class, including:

- `DURACION_MAXIMA_SIMULACION`: Maximum simulation duration
- `ANCHO_TABLERO`, `ALTO_TABLERO`: Grid dimensions
- `POBLACION_INICIAL_ANIMALES`, `POBLACION_INICIAL_PLANTAS`: Initial population sizes
- `ENERGIA_INICIAL`: Starting energy for new entities
- `ENERGIA_MAXIMA_ANIMAL`, `ENERGIA_MAXIMA_PLANTA`: Maximum energy limits
- `ENERGIA_TRANSFERIDA`: Energy transferred during interactions
- `EDAD_REPRODUCTIVA`: Age at which entities can reproduce
- `EDAD_MAXIMA`: Maximum age before death
- `USAR_AREA_RECTANGULAR`: Toggle for rectangular spawn areas
- `USAR_VECINDAD_MOORE`: Toggle for Moore neighborhood movement

## Recent Updates

### Code Quality Improvements

1. **Class Renaming and Consolidation**
   - Renamed `Lista` to `GestorPoblacion` for better clarity of purpose
   - Consolidated screen clearing functionality from `Pantalla` into `Tablero`
   - Eliminated redundant classes to improve code organization

2. **Magic Numbers Elimination**
   - Replaced all magic numbers with named constants in `Ajustes`
   - Improved code readability and maintainability
   - Added documentation for all constants

3. **Project Structure Organization**
   - Separated source files (`.java`) and compiled files (`.class`)
   - Source files in `/src/main/java/isla/`
   - Compiled files in `/target/classes/isla/`

4. **Code Refactoring**
   - Encapsulated logic in meaningful methods
   - Improved thread handling with `ManejadorHilos` class
   - Enhanced error handling
   - Maintained Spanish naming conventions
   - Better organized population management functionality

## Running the Simulation

1. Compile the project:
```bash
javac -d target/classes src/main/java/isla/*.java
```

2. Run the simulation:
```bash
java -cp target/classes isla.Main
```

## Output

The simulation provides two types of output:

1. **Visual Output**: Real-time grid visualization showing:
   - Animals (represented by letters starting from 'A')
   - Plants (represented by letters starting from 'o')
   - Empty cells (represented by '.')

2. **CSV Output**: Data logging with columns:
   - Time
   - Number of Animals
   - Number of Plants
   - Births
   - Deaths
   - Events

## Contributing

When contributing to this project, please:

1. Maintain Spanish naming conventions for consistency
2. Document any new constants added to `Ajustes`
3. Follow existing code organization patterns
4. Add appropriate error handling
5. Test changes thoroughly before submitting

## Future Improvements

Potential areas for enhancement:

- Enhanced error handling in all classes
- Additional documentation throughout the codebase
- Unit tests implementation
- New features for the ecosystem simulation
- Performance optimizations
- Additional configuration options
