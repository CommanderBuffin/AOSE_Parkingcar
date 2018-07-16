;; problem file: park-prob.pddl

(define (problem park-prob)
	(:domain park-domain)
	(:objects c0 c1 c2 c3 s0 s1 s2 s3 
		g110 g111 g112 g113 g114 g115 g116
		g100 g101 g102 g103 g104 g105 g106
		g90 g91 g92 g93 g94 g95 g96
		g80 g81 g82 g83 g84 g85 g86
		g70 g71 g72 g73 g74 g75 g76
		g60 g61 g62 g63 g64 g65 g66
		g50 g51 g52 g53 g54 g55 g56
		g40 g41 g42 g43 g44 g45 g46
		g30 g31 g32 g33 g34 g35 g36
		g20 g21 g22 g23 g24 g25 g26
		g10 g11 g12 g13 g14 g15 g16
		g00 g01 g02 g03 g04 g05 g06
	)
	(:init (and (car c0) (on c0 g00)
			(slowcar c1) (on c1 g32) (empty c1)
			(slowcar c2) (on c2 g60) (empty c2)
			(slowcar c3) (on c3 g24) (empty c3)
			
			(right g00 g01) (right g01 g02) (right g02 g03) (right g03 g04) (right g04 g05) (right g05 g06)
			(right g10 g11) (right g11 g12) (right g12 g13) (right g13 g14) (right g14 g05) (right g15 g16)
			(right g20 g21) (right g21 g22) (right g22 g23) (right g23 g24) (right g24 g25) (right g25 g26)
			(right g30 g31) (right g31 g32) (right g32 g33) (right g33 g34) (right g34 g35) (right g35 g36)
			(right g40 g41) (right g41 g42) (right g42 g43) (right g43 g44) (right g44 g45) (right g45 g46)
			(right g50 g51) (right g51 g52) (right g52 g53) (right g53 g54) (right g54 g55) (right g55 g56)
			(right g60 g61) (right g61 g62) (right g62 g63) (right g63 g64) (right g64 g65) (right g65 g66)
			(right g70 g71) (right g71 g72) (right g72 g73) (right g73 g74) (right g74 g75) (right g75 g76)
			(right g80 g81) (right g81 g82) (right g82 g83) (right g83 g84) (right g84 g85) (right g85 g86)
			(right g90 g91) (right g91 g92) (right g92 g93) (right g93 g94) (right g94 g95) (right g95 g96) 
			(right g100 g101) (right g101 g102) (right g102 g103) (right g103 g104) (right g104 g105) (right g105 g106)
			(right g110 g111) (right g111 g112) (right g112 g113) (right g113 g114) (right g114 g115) (right g115 g116) 
	
			(up g00 g10) (up g10 g20) (up g20 g30) (up g30 g40) (up g40 g50) (up g50 g60) (up g60 g70) (up g70 g80) (up g80 g90) (up g90 g100) (up g100 g110)  
			(up g01 g11) (up g11 g21) (up g21 g31) (up g31 g41) (up g41 g51) (up g51 g61) (up g61 g71) (up g71 g81) (up g81 g91) (up g91 g101) (up g101 g111)  
			(up g02 g12) (up g12 g22) (up g22 g32) (up g32 g42) (up g42 g52) (up g52 g62) (up g62 g72) (up g72 g82) (up g82 g92) (up g92 g102) (up g102 g112)  
			(up g03 g13) (up g13 g23) (up g23 g33) (up g33 g43) (up g43 g53) (up g53 g63) (up g63 g73) (up g73 g83) (up g83 g93) (up g93 g103) (up g103 g113)  
			(up g04 g14) (up g14 g24) (up g24 g34) (up g34 g44) (up g44 g54) (up g54 g64) (up g64 g74) (up g74 g84) (up g84 g94) (up g94 g104) (up g104 g114)  
			(up g05 g15) (up g15 g25) (up g25 g35) (up g35 g45) (up g45 g55) (up g55 g65) (up g65 g75) (up g75 g85) (up g85 g95) (up g95 g105) (up g105 g115)  
			(up g06 g16) (up g16 g26) (up g26 g36) (up g36 g46) (up g46 g56) (up g56 g66) (up g66 g76) (up g76 g86) (up g86 g96) (up g96 g106) (up g106 g116)  
			
			(free g01) (free g02) (free g03) (free g04) (free g05) (free g06)
			(free g12) (free g14) (free g15) (free g16)
			(free g22) 
			(free g30) (free g31) (free g34) (free g35) (free g36)
			(free g40) (free g41) (free g42) (free g43) (free g44) (free g45) (free g46)
			(free g50) (free g52) (free g53) (free g54) (free g55)
			(free g61) (free g62) (free g63) (free g65)
			(free g70) (free g71) (free g72) (free g73) (free g74) (free g75) (free 76)
			(free g80) (free g81) (free g82) (free g84) (free g85)
			(free g90) (free g92) (free g94) (free g95)
			(free g100) (free g101) (free g102) (free g104) (free g105) (free g106)
			(free g110) (free g111) (free g112) (free g113) (free g114) (free g115) (free g116)
			
			
	))
	(:goal (and (on c0 g106) (on c1 g110) (on c2 g112) (on c3 g115)))
)