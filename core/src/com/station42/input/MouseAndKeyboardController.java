package com.station42.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;

public class MouseAndKeyboardController implements Controller {
	public static final MouseAndKeyboardController None = new MouseAndKeyboardController("No player", -1, -1, -1, -1, -1, -1);
	public static final MouseAndKeyboardController WASD = new MouseAndKeyboardController("WASD - QE", Keys.W, Keys.A, Keys.S, Keys.D, Keys.Q, Keys.E);
	public static final MouseAndKeyboardController TFGH = new MouseAndKeyboardController("TFGH - RY", Keys.T, Keys.F, Keys.G, Keys.H, Keys.R, Keys.Y);
	public static final MouseAndKeyboardController IJKL = new MouseAndKeyboardController("IJKL - UO", Keys.I, Keys.J, Keys.K, Keys.L, Keys.U, Keys.O);
	public static final MouseAndKeyboardController ARROWS = new MouseAndKeyboardController("Arrows - Left Mouse/Right Mouse", Keys.UP, Keys.LEFT, Keys.DOWN, Keys.RIGHT, -1, -1);
	public final int W, A, S, D, Q, E;
	public final String name;
	private MouseAndKeyboardController(String name, int w, int a, int s, int d, int q, int e) {
		this.name = name;
		W = w;
		A = a;
		S = s;
		D = d;
		Q = q;
		E = e;
	}
	@Override
	public boolean getButton(int buttonCode) {
		return false;
	}

	@Override
	public float getAxis(int axisCode) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public PovDirection getPov(int povCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getSliderX(int sliderCode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getSliderY(int sliderCode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Vector3 getAccelerometer(int accelerometerCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAccelerometerSensitivity(float sensitivity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Keyboard: " + name;
	}

	@Override
	public void addListener(ControllerListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeListener(ControllerListener listener) {
		// TODO Auto-generated method stub
		
	}
	
	public String toString() {
		return getName();
	}
	
}
