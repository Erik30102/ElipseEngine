package Elipse.Core.Maths;

/**
 * The Color class represents a color in the RGB color space. It encapsulates
 * the red, green, and blue components of the color.
 */
public class Color {

	/** The red component of the color (0-255). */
	private int red;

	/** The green component of the color (0-255). */
	private int green;

	/** The blue component of the color (0-255). */
	private int blue;

	/**
	 * Constructs a Color object with the specified red, green, and blue components.
	 *
	 * @param red   the red component of the color (0-255)
	 * @param green the green component of the color (0-255)
	 * @param blue  the blue component of the color (0-255)
	 */
	public Color(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	/**
	 * Constructs a Color object with the specified hexadecimal color
	 * representation.
	 *
	 * @param hex the hexadecimal color representation (e.g., 0xRRGGBB)
	 */
	public Color(int hex) {
		// Extract red, green, and blue components from the hexadecimal color
		this.red = (hex >> 16) & 0xFF;
		this.green = (hex >> 8) & 0xFF;
		this.blue = hex & 0xFF;
	}

	public Color() {
		this(255, 255, 255);
	}

	/**
	 * Returns the red component of the color.
	 *
	 * @return the red component of the color
	 */
	public int getRed() {
		return red;
	}

	/**
	 * Returns the green component of the color.
	 *
	 * @return the green component of the color
	 */
	public int getGreen() {
		return green;
	}

	/**
	 * Returns the blue component of the color.
	 *
	 * @return the blue component of the color
	 */
	public int getBlue() {
		return blue;
	}

	/**
	 * Sets the red component of the color.
	 *
	 * @param red the red component of the color (0-255)
	 */
	public void setRed(int red) {
		this.red = red;
	}

	/**
	 * Sets the green component of the color.
	 *
	 * @param green the green component of the color (0-255)
	 */
	public void setGreen(int green) {
		this.green = green;
	}

	/**
	 * Sets the blue component of the color.
	 *
	 * @param blue the blue component of the color (0-255)
	 */
	public void setBlue(int blue) {
		this.blue = blue;
	}

	/**
	 * Returns the hexadecimal representation of the color.
	 *
	 * @return the hexadecimal representation of the color
	 */
	public int toHex() {
		return (red << 16) | (green << 8) | blue;
	}

	/**
	 * Turns the Color into a Vector3 which can be used by shaders
	 * 
	 * @return a Vector3 representing the color
	 */
	public Vector3 toShaderVector() {
		return new Vector3(red / 255f, green / 255f, blue / 255f);
	}

	/**
	 * Sets the color using the specified red, green, and blue components.
	 *
	 * @param red   the red component of the color (0-255)
	 * @param green the green component of the color (0-255)
	 * @param blue  the blue component of the color (0-255)
	 */
	public void setRGB(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	/**
	 * Returns a string representation of the color.
	 *
	 * @return a string representation of the color
	 */
	@Override
	public String toString() {
		return "Color{" +
				"red=" + red +
				", green=" + green +
				", blue=" + blue +
				'}';
	}
}
