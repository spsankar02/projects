def isomorphic(s,t):
    if len(s) != len(t):
        return False
    s_to_t = {}
    t_to_s = {}
    for ch_s,ch_t in zip(s,t):
        if ch_s in s_to_t and s_to_t[ch_s] != ch_t:
            return False
        if ch_t in t_to_s and t_to_s[ch_t] != ch_s:
            return False
        s_to_t[ch_s] = ch_t
        t_to_s[ch_t] = ch_s
    return True
print(isomorphic("paper","title"))