#!/bin/bash
# Change Read/Write/Execute Permission to Run: chmod +x firewall_test_dmz.sh
# To Execute: ./firewall_test_dmz.sh

echo ""
echo "Endpoint IP Address: $(hostname -I | awk '{print $1}')"
echo "Executing DMZ to Firewall Test Script..."
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
            echo "❌ Test Failed or Timed Out"
        fi
    fi
}

# Internal DMZ
echo "Testing  DMZ → DMZ Servers" 
check_connection "DMZ → DMZ Web Server (HTTP) using Lynx" "lynx -dump www.talos.edu"
check_connection "DMZ → DMZ Mail Server (SMTP) using Netcat" "nc -zv mail.talos.edu 25"
check_connection "DMZ → DMZ DNS Server (ICMP)" "ping -c 5 81.17.69.10" 
check_connection "DMZ → DMZ Mail Server (ICMP)" "ping -c 5 81.17.69.12" 
echo ""

# External Services (Allowed by Firewall)
echo "Testing DMZ → External Services" 
check_connection "DMZ → Delos Web Server (HTTP) using Lynx" "lynx -dump www.delos.edu"
check_connection "DMZ → Delos Mail Server (SMTP) using Netcat" "nc -zv mail.delos.edu 25" "yes"
echo ""

# Talos Internal Servers (Blocked by Firewall)
echo "Testing DMZ → Talos Internal Servers" 
check_connection "DMZ → Talos Intranet Server (HTTP) using Lynx" "lynx -dump 81.17.63.10" "yes"
check_connection "DMZ → Talos Local Web Server (HTTP) using Lynx" "lynx -dump 81.17.181.10" "yes"
# SSH Access (Blocked by Firewall)
check_connection "DMZ → Talos Intranet Server (SSH)" "ssh -o ConnectTimeout=5 muni@81.17.63.10" "yes"
check_connection "DMZ → Talos Local Web Server (SSH)" "ssh -o ConnectTimeout=5 muni@81.17.181.10" "yes"
check_connection "DMZ → Talos Router R3 (SSH)" "ssh -o ConnectTimeout=5 muni@81.17.182.2" "yes"
# Ping Tests (Blocked by Firewall)
check_connection "DMZ → Talos Intranet Web Server (ICMP)" "ping -c 5 81.17.63.10" "yes"
check_connection "DMZ → Talos Local Web Server (ICMP)" "ping -c 5 81.17.181.10" "yes"
check_connection "DMZ → Talos Router R3 Server (ICMP)" "ping -c 5 81.17.69.1"

echo ""
echo "✅ Firewall Test Completed."
echo ""
