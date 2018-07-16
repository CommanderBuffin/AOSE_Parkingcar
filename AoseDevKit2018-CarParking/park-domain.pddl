;; domain file: park-domain.pddl

(define (domain park-domain)
	(:requirements :strips)
	
	(:predicates (on ?x ?y)
				 (right ?l ?r)
				 (up ?d ?u)
				 (free ?c)
				 (waited ?x)
				 (car ?x)
				 (slowcar ?x)
				 (empty ?x)
				 (me ?x)
	)
	
	(:action move-right
		:parameters(?ob ?c1 ?c2)
		:precondition (and (right ?c1 ?c2) (free ?c2) (car ?ob) (on ?ob ?c1) (me ?ob))
		:effect (and (free ?c1) (on ?ob ?c2) (not (free ?c2)) )
	)

	(:action move-left
		:parameters(?ob ?c1 ?c2)
		:precondition (and (right ?c1 ?c2) (free ?c1) (car ?ob) (on ?ob ?c2) (me ?ob))
		:effect (and (free ?c2) (on ?ob ?c1) (not (free ?c1)) )
	)

	(:action move-up
		:parameters(?ob ?c1 ?c2)
		:precondition (and (up ?c1 ?c2) (free ?c2) (car ?ob) (on ?ob ?c1) (me ?ob))
		:effect (and (free ?c1) (on ?ob ?c2) (not (free ?c2)) )
	)

	(:action move-down
		:parameters(?ob ?c1 ?c2)
		:precondition (and (up ?c1 ?c2) (free ?c1) (car ?ob) (on ?ob ?c2) (me ?ob))
		:effect (and (free ?c2) (on ?ob ?c1) (not (free ?c1)) )
	)
	
	
	(:action wait
		:parameters(?ob)
		:precondition(and (slowcar ?ob) (empty ?ob) (me ?ob))
		:effect(and (waited ?ob) (not (empty ?ob)) )
	)
	
	(:action move-right-wait
		:parameters(?ob ?c1 ?c2)
		:precondition (and (right ?c1 ?c2) (free ?c2) (waited ?ob) (slowcar ?ob) (on ?ob ?c1) (me ?ob))
		:effect (and (free ?c1) (on ?ob ?c2) (empty ?ob) (not (free ?c2)) (not (waited ?ob)) )
	)

	(:action move-left-wait
		:parameters(?ob ?c1 ?c2)
		:precondition (and (right ?c1 ?c2) (free ?c1) (waited ?ob) (slowcar ?ob) (on ?ob ?c2) (me ?ob))
		:effect (and (free ?c2) (on ?ob ?c1) (empty ?ob) (not (free ?c1)) (not (waited ?ob)) )
	)

	(:action move-up-wait
		:parameters(?ob ?c1 ?c2)
		:precondition (and (up ?c1 ?c2) (free ?c2) (waited ?ob) (slowcar ?ob) (on ?ob ?c1) (me ?ob))
		:effect (and (free ?c1) (on ?ob ?c2) (empty ?ob) (not (free ?c2)) (not (waited ?ob)) )
	)

	(:action move-down-wait
		:parameters(?ob ?c1 ?c2)
		:precondition (and (up ?c1 ?c2) (free ?c1) (waited ?ob) (slowcar ?ob) (on ?ob ?c2) (me ?ob))
		:effect (and (free ?c2) (on ?ob ?c1) (empty ?ob) (not (free ?c1)) (not (waited ?ob)) )
	)
)