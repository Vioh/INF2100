PC = java -jar /hom/dag/Kurs/inf2100/2016/Pascal-kompilator/pascal2016.jar

answers:	10star.res easter.res gcd.res mini.res opers.res primes.res

10star.res:	10star
	./10star >10star.res
10star:		10star.pas
	$(PC) 10star.pas

easter.res:	easter
	./easter >easter.res
easter:		easter.pas
	$(PC) easter.pas

gcd.res:	gcd
	./gcd >gcd.res
gcd:		gcd.pas
	$(PC) gcd.pas

mini.res:	mini
	./mini >mini.res
mini:		mini.pas
	$(PC) mini.pas

opers.res:	opers
	./opers >opers.res
opers:		opers.pas
	$(PC) opers.pas

primes.res:	primes
	./primes >primes.res
primes:		primes.pas
	$(PC) primes.pas


clean:
	rm -f 10star easter gcd mini opers primes *.log *.res *.s *~
