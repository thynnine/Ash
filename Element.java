import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class Element{
    
    private int number;
    private String abbr;
    private String name;
    private String electrons;
    private double mass;
    private double radius;
    private Color color;
    
    public static final int E_H = 1;
    public static final int E_He = 2;
    public static final int E_Li = 3;
    public static final int E_Be = 4;
    public static final int E_B = 5;
    public static final int E_C = 6;
    public static final int E_N = 7;
    public static final int E_O = 8;
    public static final int E_F = 9;
    public static final int E_Ne = 10;
    public static final int E_Na = 11;
    public static final int E_Mg = 12;
    public static final int E_Al = 13;
    public static final int E_Si = 14;
    public static final int E_P = 15;
    public static final int E_S = 16;
    public static final int E_Cl = 17;
    public static final int E_Ar = 18;
    public static final int E_K = 19;
    public static final int E_Ca = 20;
    public static final int E_Sc = 21;
    public static final int E_Ti = 22;
    public static final int E_V = 23;
    public static final int E_Cr = 24;
    public static final int E_Mn = 25;
    public static final int E_Fe = 26;
    public static final int E_Co = 27;
    public static final int E_Ni = 28;
    public static final int E_Cu = 29;
    public static final int E_Zn = 30;
    public static final int E_Ga = 31;
    public static final int E_Ge = 32;
    public static final int E_As = 33;
    public static final int E_Se = 34;
    public static final int E_Br = 35;
    public static final int E_Kr = 36;
    public static final int E_Rb = 37;
    public static final int E_Sr = 38;
    public static final int E_Y = 39;
    public static final int E_Zr = 40;
    public static final int E_Nb = 41;
    public static final int E_Mo = 42;
    public static final int E_Tc = 43;
    public static final int E_Ru = 44;
    public static final int E_Rh = 45;
    public static final int E_Pd = 46;
    public static final int E_Ag = 47;
    public static final int E_Cd = 48;
    public static final int E_In = 49;
    public static final int E_Sn = 50;
    public static final int E_Sb = 51;
    public static final int E_Te = 52;
    public static final int E_I = 53;
    public static final int E_Xe = 54;
    public static final int E_Cs = 55;
    public static final int E_Ba = 56;
    public static final int E_La = 57;
    public static final int E_Ce = 58;
    public static final int E_Pr = 59;
    public static final int E_Nd = 60;
    public static final int E_Pm = 61;
    public static final int E_Sm = 62;
    public static final int E_Eu = 63;
    public static final int E_Gd = 64;
    public static final int E_Tb = 65;
    public static final int E_Dy = 66;
    public static final int E_Ho = 67;
    public static final int E_Er = 68;
    public static final int E_Tm = 69;
    public static final int E_Yb = 70;
    public static final int E_Lu = 71;
    public static final int E_Hf = 72;
    public static final int E_Ta = 73;
    public static final int E_W = 74;
    public static final int E_Re = 75;
    public static final int E_Os = 76;
    public static final int E_Ir = 77;
    public static final int E_Pt = 78;
    public static final int E_Au = 79;
    public static final int E_Hg = 80;
    public static final int E_Tl = 81;
    public static final int E_Pb = 82;
    public static final int E_Bi = 83;
    public static final int E_Po = 84;
    public static final int E_At = 85;
    public static final int E_Rn = 86;
    public static final int E_Fr = 87;
    public static final int E_Ra = 88;
    public static final int E_Ac = 89;
    public static final int E_Th = 90;
    public static final int E_Pa = 91;
    public static final int E_U = 92;
    public static final int E_Np = 93;
    public static final int E_Pu = 94;
    public static final int E_Am = 95;
    public static final int E_Cm = 96;
    public static final int E_Bk = 97;
    public static final int E_Cf = 98;
    public static final int E_Es = 99;
    public static final int E_Fm = 100;
    public static final int E_Md = 101;
    public static final int E_No = 102;
    public static final int E_Lr = 103;
    public static final int E_Rf = 104;
    public static final int E_Db = 105;
    public static final int E_Sg = 106;
    public static final int E_Bh = 107;
    public static final int E_Hs = 108;
    public static final int E_Mt = 109;
    public static final int E_Ds = 110;
    public static final int E_Rg = 111;
    public static final int E_Cn = 112;
    public static final int E_Uut = 113;
    public static final int E_Uuq = 114;
    public static final int E_Uup = 115;
    public static final int E_Uuh = 116;
    public static final int E_Uus = 117;
    public static final int E_Uuo = 118;
    public static final int E_Uue = 119;

    public Element(int index){
	
	switch(index){
	case E_H:
	    number = 1;
	    mass = 1.008;
	    radius = 1.2;
	    electrons = "1s1";
	    color = new Color(255,255,255,255);
	    name = "hydrogen";
	    abbr = "H";	
	    break;
	case E_He:
	    number = 2;
	    mass = 4.003;
	    radius = 1.4;
	    electrons = "1s2";
	    color = new Color(217,255,255,255);
	    name = "helium";
	    abbr = "He";	
	    break;
	case E_Li:
	    number = 3;
	    mass = 6.941;
	    radius = 1.8;
	    electrons = "1s2 2s1";
	    color = new Color(204,128,255,255);
	    name = "lithium";
	    abbr = "Li";	
	    break;	
	case E_Be:
	    number = 4;
	    mass = 9.012;
	    radius = 1.5;
	    electrons = "1s2 2s2";
	    color = new Color(194,255,0,255);
	    name = "beryllium";
	    abbr = "Be";	
	    break;	
	case E_B:
	    number = 5;
	    mass = 10.811;
	    radius = 1.9;
	    electrons = "[He] 2s2 2p1";
	    color = new Color(255,181,181,255);
	    name = "boron";
	    abbr = "B";	
	    break;	
	case E_C:
	    number = 6;
	    mass = 12.011;
	    radius = 1.7;
	    electrons = "[He] 2s2 2p2";
	    color = new Color(144,144,144,255);
	    name = "carbon";
	    abbr = "C";	
	    break;	
	case E_N:
	    number = 7;
	    mass = 14.007;
	    radius = 1.7;
	    electrons = "[He] 2s2 2p3";
	    color = new Color(48,80,248,255);
	    name = "nitrogen";
	    abbr = "N";	
	    break;	
	case E_O:
	    number = 8;
	    mass = 15.999;
	    radius = 1.5;
	    electrons = "[He] 2s2 2p4";
	    color = new Color(255,13,13,255);
	    name = "oxygen";
	    abbr = "O";	
	    break;
	case E_F:
	    name = "fluorine";
	    abbr = "F";	
	    number = 9;
	    mass = 18.998;
	    electrons = "[He] 2s2 2p5";
	    radius = 1.5;
	    color = new Color(144,224,80,255);
	    break;
	case E_Ne:
	    name = "neon";
	    abbr = "Ne";	
	    number = 10;
	    mass = 20.180;
	    electrons = "[He] 2s2 2p6";
	    radius = 1.5;
	    color = new Color(179,227,245,255);
	    break;
	case E_Na:
	    name = "sodium";
	    abbr = "Na";	
	    number = 11;
	    mass = 22.988;
	    electrons = "[Ne] 3s1";
	    radius = 2.3;
	    color = new Color(171,92,242,255);
	    break;
	case E_Mg:
	    name = "magnesium";
	    abbr = "Mg";	
	    number = 12;
	    mass = 24.305;
	    electrons = "[Ne] 3s2";
	    radius = 1.7;
	    color = new Color(138,255,0,255);
	    break;
	case E_Al:
	    name = "aluminium";
	    abbr = "Al";	
	    number = 13;
	    mass = 26.982;
	    electrons = "[Ne] 3s2 3p1";
	    radius = 1.8;
	    color = new Color(191,166,166,255);
	    break;
	case E_Si:
	    name = "silicon";
	    abbr = "Si";	
	    number = 14;
	    mass = 28.086;
	    electrons = "[Ne] 3s2 3p2";
	    radius = 2.1;
	    color = new Color(240,200,160,255);
	    break;
	case E_P:
	    name = "phosphorus";
	    abbr = "P";	
	    number = 15;
	    mass = 30.974;
	    electrons = "[Ne] 3s2 3p3";
	    radius = 1.8;
	    color = new Color(255,128,0,255);
	    break;
	case E_S:
	    name = "sulfur";
	    abbr = "S";	
	    number = 16;
	    mass = 32.065;
	    electrons = "[Ne] 3s2 3p4";
	    radius = 1.8;
	    color = new Color(255,255,48,255);
	    break;
	case E_Cl:
	    name = "chlorine";
	    abbr = "Cl";
	    number = 17;
	    mass = 35.453;
	    electrons = "[Ne] 3s2 3p5";
	    radius = 1.8;
	    color = new Color(31,240,31,255);
	    break;
	case E_Ar:
	    name = "argon";
	    abbr = "Ar";
	    number = 18;
	    mass = 39.948;
	    electrons = "[Ne] 3s2 3p6";
	    radius = 1.9;
	    color = new Color(128,209,227,255);
	    break;
	case E_K:
	    name = "potassium";
	    abbr = "K";
	    number = 19;
	    mass = 39.098;
	    electrons = "[Ar] 4s1";
	    radius = 2.8;
	    color = new Color(143,64,212,255);
	    break;
	case E_Ca:
	    name = "calsium";
	    abbr = "Ca";
	    number = 20;
	    mass = 40.078;
	    electrons = "[Ar] 4s2";
	    radius = 2.3;
	    color = new Color(61,255,0,255);
	    break;
	case E_Sc:
	    name = "scandium";
	    abbr = "Sc";
	    number = 21;
	    mass = 44.956;
	    electrons = "[Ar] 3d1 4s2";
	    radius = 2.1;
	    color = new Color(230,230,230,255);
	    break;
	case E_Ti:
	    name = "titanium";
	    abbr = "Ti";
	    number = 22;
	    mass = 47.867;
	    electrons = "[Ar] 3d2 4s2";
	    radius = 2.0;
	    color = new Color(191,194,199,255);
	    break;
	case E_V:
	    name = "vanadium";
	    abbr = "V";
	    number = 23;
	    mass = 50.942;
	    electrons = "[Ar] 3d3 4s2";
	    radius = 1.9;
	    color = new Color(166,166,171,255);
	    break;
	case E_Cr:
	    name = "chromium";
	    abbr = "Cr";
	    number = 24;
	    mass = 51.996;
	    electrons = "[Ar] 3d4 4s2";
	    radius = 1.8;
	    color = new Color(138,153,199,255);
	    break;
	case E_Mn:
	    name = "manganese";
	    abbr = "Mn";
	    number = 25;
	    mass = 54.938;
	    electrons = "[Ar] 3d5 4s2";
	    radius = 1.7;
	    color = new Color(156,122,199,255);
	    break;
	case E_Fe:
	    name = "iron";
	    abbr = "Fe";
	    number = 26;
	    mass = 55.845;
	    electrons = "[Ar] 3d6 4s2";
	    radius = 1.7;
	    color = new Color(224,102,51,255);
	    break;
	case E_Co:
	    name = "cobalt";
	    abbr = "Co";
	    number = 27;
	    mass = 58.933;
	    electrons = "[Ar] 3d7 4s2";
	    radius = 1.6;
	    color = new Color(240,144,160,255);
	    break;
	case E_Ni:
	    name = "nickel";
	    abbr = "Ni";
	    number = 28;
	    mass = 58.693;
	    electrons = "[Ar] 3d9 4s1";
	    radius = 1.6;
	    color = new Color(80,208,80,255);
	    break;
	case E_Cu:
	    name = "copper";
	    abbr = "Cu";
	    number = 29;
	    mass = 63.546;
	    electrons = "[Ar] 3d10 4s1";
	    radius = 1.4;
	    color = new Color(200,128,51,255);
	    break;
	case E_Zn:
	    name = "zinc";
	    abbr = "Zn";
	    number = 30;
	    mass = 65.38;
	    electrons = "[Ar] 3d10 4s2";
	    radius = 1.4;
	    color = new Color(125,128,176,255);
	    break;
	case E_Ga:
	    name = "gallium";
	    abbr = "Ga";
	    number = 31;
	    mass = 69.723;
	    electrons = "[Ar] 3d10 4s2 4p1";
	    radius = 1.9;
	    color = new Color(194,143,143,255);
	    break;
	case E_Ge:
	    name = "germanium";
	    abbr = "Ge";
	    number = 32;
	    mass = 72.64;
	    electrons = "[Ar] 3d10 4s2 4p2";
	    radius = 2.1;
	    color = new Color(102,143,143,255);
	    break;
	case E_As:
	    name = "arsenic";
	    abbr = "As";
	    number = 33;
	    mass = 74.921;
	    electrons = "[Ar] 3d10 4s2 4p3";
	    radius = 1.9;
	    color = new Color(189,128,227,255);
	    break;
	case E_Se:
	    name = "selenium";
	    abbr = "Se";
	    number = 34;
	    mass = 78.96;
	    electrons = "[Ar] 3d10 4s2 4p4";
	    radius = 1.9;
	    color = new Color(255,161,0,255);
	    break;
	case E_Br:
	    name = "bromine";
	    abbr = "Br";
	    number = 35;
	    mass = 79.904;
	    electrons = "[Ar] 3d10 4s2 4p5";
	    radius = 1.9;
	    color = new Color(166,41,41,255);
	    break;
	case E_Kr:
	    name = "krypton";
	    abbr = "Kr";
	    number = 36;
	    mass = 83.798;
	    electrons = "[Ar] 3d10 4s2 4p6";
	    radius = 2.0;
	    color = new Color(92,184,209,255);
	    break;
	case E_Rb:
	    name = "rubidium";
	    abbr = "Rb";
	    number = 37;
	    mass = 85.467;
	    electrons = "[Kr] 5s1";
	    radius = 3.0;
	    color = new Color(112,46,176,255);
	    break;
	case E_Sr:
	    name = "strontium";
	    abbr = "Sr";
	    number = 38;
	    mass = 87.62;
	    electrons = "[Kr] 5s2";
	    radius = 2.5;
	    color = new Color(0,255,0,255);
	    break;
	case E_Y:
	    name = "yttrium";
	    abbr = "Y";
	    number = 39;
	    mass = 88.909;
	    electrons = "[Kr] 4d1 5s2";
	    radius = 1.9;
	    color = new Color(148,255,255,255);
	    break;
	case E_Zr:
	    name = "zirconium";
	    abbr = "Zr";
	    number = 40;
	    mass = 91.224;
	    electrons = "[Kr] 4d2 5s2";
	    radius = 1.9;
	    color = new Color(148,224,224,255);
	    break;
	case E_Nb:
	    name = "niobium";
	    abbr = "Nb";
	    number = 41;
	    mass = 92.906;
	    electrons = "[Kr] 4d4 5s1";
	    radius = 1.8;
	    color = new Color(115,194,201,255);
	    break;
	case E_Mo:
	    name = "molybdenum";
	    abbr = "Mo";
	    number = 42;
	    mass = 95.94;
	    electrons = "[Kr] 4d5 5s1";
	    radius = 1.7;
	    color = new Color(84,181,181,255);
	    break;
	case E_Tc:
	    name = "technetium";
	    abbr = "Tc";
	    number = 43;
	    mass = 98;
	    electrons = "[Kr] 4d5 5s2";
	    radius = 1.6;
	    color = new Color(59,158,158,255);
	    break;
	case E_Ru:
	    name = "ruthenium";
	    abbr = "Ru";
	    number = 44;
	    mass = 101.07;
	    electrons = "[Kr] 4d7 5s1";
	    radius = 1.6;
	    color = new Color(36,143,143,255);
	    break;
	case E_Rh:
	    name = "rhodium";
	    abbr = "Rh";
	    number = 45;
	    mass = 102.906;
	    electrons = "[Kr] 4d8 5s1";
	    radius = 1.6;
	    color = new Color(10,125,140,255);
	    break;
	case E_Pd:
	    name = "palladium";
	    abbr = "Pd";
	    number = 46;
	    mass = 106.42;
	    electrons = "[Kr] 4d10";
	    radius = 1.6;
	    color = new Color(0,105,133,255);
	    break;
	case E_Ag:
	    name = "silver";
	    abbr = "Ag";
	    number = 47;
	    mass = 107.868;
	    electrons = "[Kr] 4d10 5s1";
	    radius = 1.7;
	    color = new Color(192,192,192,255);
	    break;
	case E_Cd:
	    name = "cadmium";
	    abbr = "Cd";
	    number = 48;
	    mass = 112.411;
	    electrons = "[Kr] 4d10 5s2";
	    radius = 1.6;
	    color = new Color(255,217,143,255);
	    break;
	case E_In:
	    name = "indium";
	    abbr = "In";
	    number = 49;
	    mass = 114.818;
	    electrons = "[Kr] 4d10 5s2 5p1";
	    radius = 1.9;
	    color = new Color(166,117,115,255);
	    break;
	case E_Sn:
	    name = "tin";
	    abbr = "In";
	    number = 50;
	    mass = 118.710;
	    electrons = "[Kr] 4d10 5s2 5p2";
	    radius = 2.2;
	    color = new Color(102,128,128,255);
	    break;
	case E_Sb:
	    name = "antimony";
	    abbr = "Sb";
	    number = 51;
	    mass = 121.760;
	    electrons = "[Kr] 4d10 5s2 5p3";
	    radius = 2.0;
	    color = new Color(158,99,181,255);
	    break;
	case E_Te:
	    name = "tellurium";
	    abbr = "Te";
	    number = 52;
	    mass = 127.60;
	    electrons = "[Kr] 4d10 5s2 5p4";
	    radius = 2.0;
	    color = new Color(212,122,0,255);
	    break;
	case E_I:
	    name = "iodine";
	    abbr = "I";
	    number = 53;
	    mass = 127.60;
	    electrons = "[Kr] 4d10 5s2 5p5";
	    radius = 2.0;
	    color = new Color(148,0,148,255);
	    break;
	case E_Xe:
	    name = "xenon";
	    abbr = "Xe";
	    number = 54;
	    mass = 131.293;
	    electrons = "[Kr] 4d10 5s2 5p6";
	    radius = 2.2;
	    color = new Color(66,158,176,255);
	    break;
	case E_Cs:
	    name = "cesium";
	    abbr = "Cs";
	    number = 55;
	    mass = 132.905;
	    electrons = "[Xe] 6s1";
	    radius = 3.4;
	    color = new Color(87,23,143,255);
	    break;
	case E_Ba:
	    name = "barium";
	    abbr = "Ba";
	    number = 56;
	    mass = 137.33;
	    electrons = "[Xe] 6s2";
	    radius = 2.7;
	    color = new Color(0,201,0,255);
	    break;
	case E_La:
	    name = "lanthanum";
	    abbr = "La";
	    number = 57;
	    mass = 138.905;
	    electrons = "[Xe] 5d1 6s2";
	    radius = 2.6;
	    color = new Color(112,212,255,255);
	    break;
	case E_Ce:
	    name = "cerium";
	    abbr = "Ce";
	    number = 58;
	    mass = 140.116;
	    electrons = "[Xe] 4f2 6s2";
	    radius = 2.6;
	    color = new Color(255,255,199,255);
	    break;
	case E_Pr:
	    name = "praseodymium";
	    abbr = "Pr";
	    number = 59;
	    mass = 140.908;
	    electrons = "[Xe] 4f3 6s2";
	    radius = 2.6;
	    color = new Color(217,255,199,255);
	    break;
	case E_Nd:
	    name = "neodymium";
	    abbr = "Ne";
	    number = 60;
	    mass = 144.242;
	    electrons = "[Xe] 4f4 6s2";
	    radius = 2.5;
	    color = new Color(199,255,199,255);
	    break;
	case E_Pm:
	    name = "promethium";
	    abbr = "Pm";
	    number = 61;
	    mass = 145;
	    electrons = "[Xe] 4f5 6s2";
	    radius = 2.4;
	    color = new Color(163,255,199,255);
	    break;
	case E_Sm:
	    name = "samarium";
	    abbr = "Sm";
	    number = 62;
	    mass = 150.36;
	    electrons = "[Xe] 4f6 6s2";
	    radius = 2.4;
	    color = new Color(143,255,199,255);
	    break;
	case E_Eu:
	    name = "europium";
	    abbr = "Eu";
	    number = 63;
	    mass = 151.964;
	    electrons = "[Xe] 4f7 6s2";
	    radius = 2.4;
	    color = new Color(97,255,199,255);
	    break;
	case E_Gd:
	    name = "gadolinium";
	    abbr = "Gd";
	    number = 64;
	    mass = 157.25;
	    electrons = "[Xe] 4f7 5d1 6s2";
	    radius = 2.4;
	    color = new Color(69,255,199,255);
	    break;
	case E_Tb:
	    name = "terbium";
	    abbr = "Tb";
	    number = 65;
	    mass = 158.925;
	    electrons = "[Xe] 4f9 6s2";
	    radius = 2.3;
	    color = new Color(48,255,199,255);
	    break;
	case E_Dy:
	    name = "dysporsium";
	    abbr = "Dy";
	    number = 66;
	    mass = 162.500;
	    electrons = "[Xe] 4f10 6s2";
	    radius = 2.3;
	    color = new Color(31,255,199,255);
	    break;
	case E_Ho:
	    name = "holmium";
	    abbr = "Ho";
	    number = 67;
	    mass = 164.930;
	    electrons = "[Xe] 4f11 6s2";
	    radius = 2.3;
	    color = new Color(0,255,156,255);
	    break;
	case E_Er:
	    name = "erbium";
	    abbr = "Er";
	    number = 68;
	    mass = 167.259;
	    electrons = "[Xe] 4f12 6s2";
	    radius = 2.2;
	    color = new Color(0,230,117,255);
	    break;
	case E_Tm:
	    name = "thulium";
	    abbr = "Tm";
	    number = 69;
	    mass = 168.934;
	    electrons = "[Xe] 4f13 6s2";
	    radius = 2.2;
	    color = new Color(0,212,82,255);
	    break;
	case E_Yb:
	    name = "ytterbium";
	    abbr = "Yb";
	    number = 70;
	    mass = 173.04;
	    electrons = "[Xe] 4f14 6s2";
	    radius = 2.1;
	    color = new Color(0,191,56,255);
	    break;
	case E_Lu:
	    name = "lutetium";
	    abbr = "Lu";
	    number = 71;
	    mass = 174.967;
	    electrons = "[Xe] 4f14 5d1 6s2";
	    radius = 2.1;
	    color = new Color(0,171,36,255);
	    break;
	case E_Hf:
	    name = "hafnium";
	    abbr = "Hf";
	    number = 72;
	    mass = 178.49;
	    electrons = "[Xe] 4f14 5d2 6s2";
	    radius = 2.0;
	    color = new Color(77,194,255,255);
	    break;
	case E_Ta:
	    name = "tantalum";
	    abbr = "Ta";
	    number = 73;
	    mass = 180.948;
	    electrons = "[Xe] 4f14 5d3 6s2";
	    radius = 1.9;
	    color = new Color(77,166,255,255);
	    break;
	case E_W:
	    name = "tungsten";
	    abbr = "Ta";
	    number = 74;
	    mass = 183.84;
	    electrons = "[Xe] 4f14 5d4 6s2";
	    radius = 1.9;
	    color = new Color(33,148,214,255);
	    break;
	case E_Re:
	    name = "rhenium";
	    abbr = "Re";
	    number = 75;
	    mass = 186.207;
	    electrons = "[Xe] 4f14 5d5 6s2";
	    radius = 1.8;
	    color = new Color(38,125,171,255);
	    break;
	case E_Os:
	    name = "osmium";
	    abbr = "Os";
	    number = 76;
	    mass = 190.23;
	    electrons = "[Xe] 4f14 5d6 6s2";
	    radius = 1.7;
	    color = new Color(38,102,150,255);
	    break;
	case E_Ir:
	    name = "iridium";
	    abbr = "Ir";
	    number = 77;
	    mass = 192.217;
	    electrons = "[Xe] 4f14 5d7 6s2";
	    radius = 1.7;
	    color = new Color(23,84,135,255);
	    break;
	case E_Pt:
	    name = "platinum";
	    abbr = "Pt";
	    number = 78;
	    mass = 195.084;
	    electrons = "[Xe] 4f14 5d9 6s1";
	    radius = 1.7;
	    color = new Color(208,208,224,255);
	    break;
	case E_Au:
	    name = "gold";
	    abbr = "Au";
	    number = 79;
	    mass = 196.967;
	    electrons = "[Xe] 4f14 5d10 6s1";
	    radius = 1.7;
	    color = new Color(255,209,35,255);
	    break;
	case E_Hg:
	    name = "mercury";
	    abbr = "Hg";
	    number = 80;
	    mass = 200.59;
	    electrons = "[Xe] 4f14 5d10 6s2";
	    radius = 1.6;
	    color = new Color(184,184,208,255);
	    break;
	case E_Tl:
	    name = "thallium";
	    abbr = "Tl";
	    number = 81;
	    mass = 204.383;
	    electrons = "[Xe] 4f14 5d10 6s2 6p1";
	    radius = 2.0;
	    color = new Color(166,84,77,255);
	    break;
	case E_Pb:
	    name = "lead";
	    abbr = "Pb";
	    number = 82;
	    mass = 207.2;
	    electrons = "[Xe] 4f14 5d10 6s2 6p2";
	    radius = 2.0;
	    color = new Color(87,89,97,255);
	    break;
	case E_Bi:
	    name = "bismuth";
	    abbr = "Bi";
	    number = 83;
	    mass = 208.980;
	    electrons = "[Xe] 4f14 5d10 6s2 6p3";
	    radius = 2.1;
	    color = new Color(158,79,181,255);
	    break;
	case E_Po:
	    name = "polonium";
	    abbr = "Po";
	    number = 84;
	    mass = 209;
	    electrons = "[Xe] 4f14 5d10 6s2 6p4";
	    radius = 2.0;
	    color = new Color(171,92,0,255);
	    break;
	case E_At:
	    name = "astatine";
	    abbr = "At";
	    number = 85;
	    mass = 210;
	    electrons = "[Xe] 4f14 5d10 6s2 6p5";
	    radius = 2.0;
	    color = new Color(117,79,69,255);
	    break;
	case E_Rn:
	    name = "radon";
	    abbr = "Rn";
	    number = 86;
	    mass = 222;
	    electrons = "[Xe] 4f14 5d10 6s2 6p6";
	    radius = 2.2;
	    color = new Color(66,130,150,255);
	    break;
	case E_Fr:
	    name = "francium";
	    abbr = "Fr";
	    number = 87;
	    mass = 223;
	    electrons = "[Rn] 7s1";
	    radius = 3.5;
	    color = new Color(66,0,102,255);
	    break;
	case E_Ra:
	    name = "radium";
	    abbr = "Ra";
	    number = 88;
	    mass = 226;
	    electrons = "[Rn] 7s2";
	    radius = 2.8;
	    color = new Color(0,125,0,255);
	    break;
	case E_Ac:
	    name = "actinium";
	    abbr = "Ac";
	    number = 89;
	    mass = 227;
	    electrons = "[Rn] 6d1 7s2";
	    radius = 2.7;
	    color = new Color(112,171,250,255);
	    break;
	case E_Th:
	    name = "thorium";
	    abbr = "Th";
	    number = 90;
	    mass = 232.038;
	    electrons = "[Rn] 6d2 7s2";
	    radius = 2.6;
	    color = new Color(0,186,255,255);
	    break;
	case E_Pa:
	    name = "protactinium";
	    abbr = "Pa";
	    number = 91;
	    mass = 231.036;
	    electrons = "[Rn] 5f2 6d1 7s2";
	    radius = 2.4;
	    color = new Color(0,161,255,255);
	    break;
	case E_U:
	    name = "uranium";
	    abbr = "U";
	    number = 92;
	    mass = 238.029;
	    electrons = "[Rn] 5f3 6d1 7s2";
	    radius = 1.9;
	    color = new Color(0,143,255,255);
	    break;
	case E_Np:
	    name = "neptunium";
	    abbr = "Np";
	    number = 93;
	    mass = 237;
	    electrons = "[Rn] 5f4 6d1 7s2";
	    radius = 1.9;
	    color = new Color(0,128,255,255);
	    break;
	case E_Pu:
	    name = "plutonium";
	    abbr = "Pu";
	    number = 94;
	    mass = 244;
	    electrons = "[Rn] 5f6 7s2";
	    radius = 1.9;
	    color = new Color(0,107,255,255);
	    break;
	case E_Am:
	    name = "americium";
	    abbr = "Am";
	    number = 95;
	    mass = 243;
	    electrons = "[Rn] 5f7 7s2";
	    radius = 1.9;
	    color = new Color(84,92,242,255);
	    break;
	case E_Cm:
	    name = "curium";
	    abbr = "Cm";
	    number = 96;
	    mass = 247;
	    electrons = "[Rn] 5f7 6d1 7s2";
	    radius = 1.9;
	    color = new Color(120,92,227,255);
	    break;
	case E_Bk:
	    name = "berkelium";
	    abbr = "Bk";
	    number = 97;
	    mass = 247;
	    electrons = "[Rn] 5f9 7s2";
	    radius = 1.9;
	    color = new Color(138,79,227,255);
	    break;
	case E_Cf:
	    name = "californium";
	    abbr = "Cf";
	    number = 98;
	    mass = 251;
	    electrons = "[Rn] 5f10 7s2";
	    radius = 1.9;
	    color = new Color(161,54,212,255);
	    break;
	case E_Es:
	    name = "einsteinium";
	    abbr = "Es";
	    number = 99;
	    mass = 252;
	    electrons = "[Rn] 5f11 7s2";
	    radius = 1.9;
	    color = new Color(179,31,212,255);
	    break;
	case E_Fm:
	    name = "fermium";
	    abbr = "Fe";
	    number = 100;
	    mass = 257;
	    electrons = "[Rn] 5f12 7s2";
	    radius = 1.9;
	    color = new Color(179,31,186,255);
	    break;
	case E_Md:
	    name = "mendelevium";
	    abbr = "Md";
	    number = 101;
	    mass = 258;
	    electrons = "[Rn] 5f13 7s2";
	    radius = 1.9;
	    color = new Color(179,13,166,255);
	    break;
	case E_No:
	    name = "nobelium";
	    abbr = "No";
	    number = 102;
	    mass = 259;
	    electrons = "[Rn] 5f14 7s2";
	    radius = 1.9;
	    color = new Color(189,13,135,255);
	    break;
	case E_Lr:
	    name = "lawrencium";
	    abbr = "Lr";
	    number = 103;
	    mass = 262;
	    electrons = "[Rn] 5f14 7s2 7p1";
	    radius = 1.9;
	    color = new Color(199,0,102,255);
	    break;
	case E_Rf:
	    name = "rutherfordium";
	    abbr = "Rf";
	    number = 104;
	    mass = 267;
	    electrons = "[Rn] 5f14 6d2 7s2";
	    radius = 1.9;
	    color = new Color(204,0,89,255);
	    break;
	case E_Db:
	    name = "dubnium";
	    abbr = "Db";
	    number = 105;
	    mass = 268;
	    electrons = "[Rn] 5f14 6d3 7s2";
	    radius = 1.9;
	    color = new Color(209,0,79,255);
	    break;
	case E_Sg:
	    name = "seaborgium";
	    abbr = "Sg";
	    number = 106;
	    mass = 271;
	    electrons = "[Rn] 5f14 6d4 7s2";
	    radius = 1.9;
	    color = new Color(217,0,69,255);
	    break;
	case E_Bh:
	    name = "bohrium";
	    abbr = "Bh";
	    number = 107;
	    mass = 270;
	    electrons = "[Rn] 5f14 6d5 7s2";
	    radius = 1.9;
	    color = new Color(224,0,56,255);
	    break;
	case E_Hs:
	    name = "hassium";
	    abbr = "Hs";
	    number = 108;
	    mass = 269;
	    electrons = "[Rn] 5f14 6d6 7s2";
	    radius = 1.9;
	    color = new Color(230,0,46,255);
	    break;
	case E_Mt:
	    name = "meitnerium";
	    abbr = "Mt";
	    number = 109;
	    mass = 278;
	    electrons = "[Rn] 5f14 6d7 7s2";
	    radius = 1.9;
	    color = new Color(235,0,38,255);
	    break;
	case E_Ds:
	    name = "darmstadium";
	    abbr = "Ds";
	    number = 110;
	    mass = 281;
	    electrons = "[Rn] 5f14 6d9 7s1";
	    radius = 1.9;
	    color = new Color(200,200,200,255);
	    break;
	case E_Rg:
	    name = "roentgenium";
	    abbr = "Rg";
	    number = 111;
	    mass = 281;
	    electrons = "[Rn] 5f14 6d10 7s1";
	    radius = 1.9;
	    color = new Color(200,200,200,255);
	    break;
	case E_Cn:
	    name = "copernicium";
	    abbr = "Cn";
	    number = 112;
	    mass = 285;
	    electrons = "[Rn] 5f14 6d10 7s2";
	    radius = 1.9;
	    color = new Color(200,200,200,255);
	    break;
	case E_Uut:
	    name = "ununtrium";
	    abbr = "Uut";
	    number = 113;
	    mass = 286;
	    electrons = "[Rn] 5f14 6d10 7s2 7p1";
	    radius = 1.9;
	    color = new Color(200,200,200,255);
	    break;
	case E_Uuq:
	    name = "ununquadium";
	    abbr = "Uuq";
	    number = 114;
	    mass = 289;
	    electrons = "[Rn] 5f14 6d10 7s2 7p2";
	    radius = 1.9;
	    color = new Color(200,200,200,255);
	    break;
	case E_Uup:
	    name = "ununpentium";
	    abbr = "Uup";
	    number = 115;
	    mass = 289;
	    electrons = "[Rn] 5f14 6d10 7s2 7p3";
	    radius = 1.9;
	    color = new Color(200,200,200,255);
	    break;
	case E_Uuh:
	    name = "ununhexium";
	    abbr = "Uuh";
	    number = 116;
	    mass = 293;
	    electrons = "[Rn] 5f14 6d10 7s2 7p4";
	    radius = 1.9;
	    color = new Color(200,200,200,255);
	    break;
	case E_Uus:
	    name = "ununseptium";
	    abbr = "Uus";
	    number = 117;
	    mass = 294;
	    electrons = "[Rn] 5f14 6d10 7s2 7p5";
	    radius = 1.9;
	    color = new Color(200,200,200,255);
	    break;
	case E_Uuo:
	    name = "ununoctium";
	    abbr = "Uuo";
	    number = 118;
	    mass = 294;
	    electrons = "[Rn] 5f14 6d10 7s2 7p6";
	    radius = 1.9;
	    color = new Color(200,200,200,255);
	    break;
	case E_Uue:
	    name = "ununennium";
	    abbr = "Uue";
	    number = 119;
	    mass = 294;
	    electrons = "[Uuo] 8s1";
	    radius = 1.9;
	    color = new Color(200,200,200,255);
	    break;

	case 0:
	    number = 120;
	    mass = 1;
	    radius = 1;
	    electrons = "";
	    color = new Color(50%PeriodicTable.SPACE,
			      180%PeriodicTable.SPACE,
			      220%PeriodicTable.SPACE,255);
	    name = "special";
	    abbr = " ";
	    break;
	default:
	    number = index;
	    mass = 1;
	    radius = 1;
	    electrons = "";
	    color = new Color((59*(index-120)+100)%PeriodicTable.SPACE,
			      (43*(index-120)+180)%PeriodicTable.SPACE,
			      (47*(index-120)+220)%PeriodicTable.SPACE,255);
	    name = "special";
	    abbr = " ";	
	    break;
	}
    }

    public int getNumber(){
	return number;
    }

    public double getMass(){
	return mass;
    }

    public String getSymbol(){
	return abbr;
    }

    public String getName(){
	return name;
    }

    public String getElectrons(){
	return electrons;
    }

    public Color getColor(){
	return color;
    }

    public double getRadius(){
	return radius;
    }


    public void setNumber(int nro){
	number = nro;
    }

    public void setMass(double ms){
	mass = ms;
    }

    public void setSymbol(String symb){
	abbr = symb;
    }

    public void setName(String full){
	name = full;
    }

    public void setElectrons(String es){
	electrons = es;
    }

    public void setColor(Color hue){
	color = hue;
    }

    public void setRadius(double rad){
	radius = rad;
    }

}