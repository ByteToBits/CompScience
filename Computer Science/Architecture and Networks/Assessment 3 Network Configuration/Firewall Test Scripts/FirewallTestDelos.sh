#!/bin/bash 
# Change Read/Write/Execute Permission to Run: chmod +x firewall_test_delos.sh
# To Execute: ./firewall_test_delos.sh

echo ""
echo "Endpoint IP Address: $(hostname -I | awk '{print $1}')"
echo "Executing Delos to Firewall Test Script..."
echo ""

check_connection() {
    local EXPECTED_BLOCKED=$3 
    echo -n "$1: "
    eval "timeout 5 $2" &> /dev/null
    if [ $? -eq 0 ]; then
        if [ "$EXPECTED_BLOCKED" == "yes" ]; then
            echo "❌ Test Failed"
        else
            echo "✅ Success"
        fi
    else
        if [ "$EXPECTED_BLOCKED" == "yes" ]; then
            echo "✅ Successfully Blocked"
        else
            echo "❌ Test Failed"
        fi
    fi
}

# Task C.1: External → DMZ Services
echo "Testing Delos → DMZ Servers" 
check_connection "Delos → DMZ Web Server (HTTP) using Lynx" "lynx -dump www.talos.edu"
check_connection "Delos → DMZ Mail Server (SMTP) using Netcat" "nc -zv mail.talos.edu 25"
check_connection "Delos → DMZ DNS Server (DNS Query)" "dig @49.66.82.10 talos.edu"
# Expected to be Blocked by Firewall
check_connection "Delos → DMZ SSH" "ssh -o ConnectTimeout=5 muni@81.17.69.11" "yes"
check_connection "Delos → DMZ ICMP" "ping -c 5 81.17.69.11" "yes"
echo ""

# Task C.2: Delos → Delos Internal Servers
echo "Testing Delos → Delos Servers" 
check_connection "Delos → Delos Web Server (HTTP) using Lynx" "lynx -dump www.delos.edu"
check_connection "Delos → Delos Mail Server (SMTP) using Netcat" "nc -zv mail.delos.edu 25"
check_connection "Delos → Delos DNS Query using Dig" "dig @49.66.82.10 delos.edu"
echo ""

# Task C.3: Delos → Talos Internal Servers (Blocked by Firewall)
echo "Testing Delos → Talos Internal Servers" 
check_connection "Delos → Talos Intranet Server (HTTP) using Lynx" "lynx -dump 81.17.63.10" "yes"
check_connection "Delos → Talos Local Web Server (HTTP) using Lynx" "lynx -dump 81.17.181.10" "yes"
# SSH Access (Blocked by Firewall)
check_connection "Delos → Talos Intranet Server (SSH)" "ssh -o ConnectTimeout=5 muni@81.17.63.10" "yes"
check_connection "Delos → Talos Local Web Server (SSH)" "ssh -o ConnectTimeout=5 muni@81.17.181.10" "yes"
check_connection "Delos → Talos Router R3 (SSH)" "ssh -o ConnectTimeout=5 muni@81.17.182.2" "yes"
# Ping Tests (Blocked by Firewall)
check_connection "Delos → Talos Intranet Web Server (ICMP)" "ping -c 5 81.17.63.10" "yes"
check_connection "Delos → Talos Local Web Server (ICMP)" "ping -c 5 81.17.181.10" "yes"
check_connection "Delos → Talos Router R3 Server (ICMP)" "ping -c 5 81.17.182.2" "yes"

echo ""
echo "✅ Firewall Test Completed."
echo ""
